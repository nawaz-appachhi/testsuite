import requests
from cassandra.cluster import Cluster
import time
from elasticsearch import Elasticsearch

cassandra_cluster = Cluster(['lgp1', 'lgp2', 'lgp3', 'lgp4'])
cassandra_conn = cassandra_cluster.connect("lgp")

client = Elasticsearch(['lgp-es1:9202', 'lgp-es2:9202', 'lgp-es3:9202', 'lgp-es4:9202'])

mismatched = []
map = {'feed-test': 'unknown:test'}

stmt = cassandra_conn.prepare("select image_url from object_entity where app_object_ref_key in ?")

all_is_well = True

for key in map:
    #mismatched.append("###################################")
    mismatched.append(key)
    developer_results = requests.get('http://contour-internal.myntra.com/contour/api/feed-banners/get-test-banners').json()
    developer = []
    mapo_developer = {}
    stories = developer_results['children'][0]['children'][0]['children']
    for i in range(1, len(stories)):
	if stories[i]['type'] in ['carousel', 'split']:
            stories[i]['props'] = None
    	if stories[i].get('props', None):
	    src = stories[i]['props']['src']
	    if src not in mapo_developer:
             	developer.append(src)
	    	mapo_developer[src] = src
            #developer.append(src)
   	else:
            j = len(stories[i]['children']) - 1
	    images = []
            while j >= 0:
		src = stories[i]['children'][j]['props']['src']
		images.append(src)
            	#developer.append(src)
            	j -= 1
	    images.sort()
	    src = ",".join(images)
	    if src not in mapo_developer:
                developer.append(src)
                mapo_developer[src] = src

    feed_objectids = []

    value = map[key]

    search_body = {
        "fields" : ["story_id", "object_id"],
        "from" : 0, "size" : 100,
        "query": {
            "bool": {
                "must": [
                    {"term":{"enabled":True}},
                    {"range":{"boost":{"gt":0,"lte":1}}}
                ],
                "must_not": [
                    {"term":{"stream":"stream-nav"}},
                    {"term":{"stream":"stream-slideshow"}}
                ]
            }
        }
    }

    search_body['query']['bool']['must'].append({"term":{"persona":value[:value.find(':')]}})
    search_body['query']['bool']['must'].append({"term":{"platform":value[value.find(':') + 1:]}})
    response = client.search(index="story_fumes", body=search_body)

    for objectid in response['hits']['hits']:
	feed_objectids.append(objectid['fields']['object_id'][0])

    results = cassandra_conn.execute(stmt, (feed_objectids,))
    feed_objectids = []
    for r in results:
        feed_objectids.append(r.image_url)
        #if ',' not in r.image_url:
        #    feed_objectids.append(r.image_url)
        #else:
        #    feed_objectids += r.image_url.split(',')

    #print "Developer count: " + str(len(developer))
    #print "ES count: " + str(len(feed_objectids))
    #print "All Good? " + str(len(developer)  == len(feed_objectids))
    mismatched.append("Developer count: " + str(len(developer)))
    mismatched.append("ES count: " + str(len(feed_objectids)))
    mismatched.append("All Good? " + str(len(developer) == len(feed_objectids)))

    dev_duplicates = False
    mapo_developer = {}
    for d in developer:
        if d in mapo_developer:
	    dev_duplicates = True
            mismatched.append("Duplicate in developer: " + d)
        else:
            mapo_developer[d] = d

    es_duplicates = False
    mapo_es = {}
    for d in feed_objectids:
        if d in mapo_es:
	    es_duplicates = True
            mismatched.append("Duplicate in ES: " + d)
        else:
            mapo_es[d] = d


    if len(developer) != len(feed_objectids) or es_duplicates or dev_duplicates:
	all_is_well = False
        if len(developer) > len(feed_objectids):
	    #print "Preset in developer but not present in ES:"
	    mismatched.append("Preset in developer but not present in ES:")
            for d in developer:
                if d not in feed_objectids:
                    mismatched.append(d)
        else:
	    #print "Preset in ES but not present in developer:"
	    mismatched.append("Preset in ES but not present in developer:")
            for d in feed_objectids:
                if d not in developer:
                    mismatched.append(d)

if not all_is_well:
    for d in mismatched:
	print d
    exit(1)
else:
    print "All Good? " + str(True)
    exit(0)
