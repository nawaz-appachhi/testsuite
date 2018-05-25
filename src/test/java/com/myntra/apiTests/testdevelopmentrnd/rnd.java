package com.myntra.apiTests.testdevelopmentrnd;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.report.ProcessingReport;
import com.github.fge.jsonschema.util.JsonLoader;
import com.myntra.apiTests.dataproviders.StyleServiceApiDP;
import com.myntra.apiTests.portalservices.styleservice.StyleServiceApiHelper;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by ashish.bajpai on 21/05/14.
 */
public class rnd extends StyleServiceApiDP
{
    static StyleServiceApiHelper styleServiceApiHelper = new StyleServiceApiHelper();
    static Logger log = Logger.getLogger(rnd.class);

    /**
     * This TestCase is used to invoke StyleService getStyleDataForSingleStyleId API and verification for response data nodes
     *
     * @param styleId
     */
    @Test(groups = { "Sanity", "MiniRegression", "Regression", "ExhaustiveRegression" },
            dataProvider="StyleServiceApiDP_getStyleDataForSingleStyleId_verifyDataNodes")
    public void StyleService_getStyleDataForSingleStyleId_verifyDataNodes_jsonpath(String styleId)
    {
        RequestGenerator getStyleDataForSingleStyleIdReqGen = styleServiceApiHelper.getStyleDataForSingleStyleId(styleId);
        String getStyleDataForSingleStyleIdResponse = getStyleDataForSingleStyleIdReqGen.respvalidate.returnresponseasstring();
        System.out.println("Printing StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
        log.info("Printing StyleService getStyleDataForSingleStyleId API response : \n\n"+getStyleDataForSingleStyleIdResponse+"\n");
        //String response = req.respvalidate.returnresponseasstring();
/*

        AssertJUnit.assertTrue("Error while invoking StyleService getStyleDataForSingleStyleId API",
                getStyleDataForSingleStyleIdReqGen.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.getNodePath(), true).contains(SUCCESS_STATUS_TYPE));

        AssertJUnit.assertTrue("Invalid data nodes in StyleService getStyleDataForSingleStyleId API response",
                getStyleDataForSingleStyleIdReqGen.respvalidate.DoesNodesExists(StyleServiceNodes.getDataNodes()));

        AssertJUnit.assertEquals("StyleService getStyleDataForSingleStyleId API request and response styleId are not same", Long.parseLong(styleId),
                Long.parseLong(getStyleDataForSingleStyleIdReqGen.respvalidate.NodeValue(StyleServiceNodes.STYLE_DATA_ID.getNodePath(), true)));
*/

        //JsonPath.read(getStyleDataForSingleStyleIdResponse, "$.data.queryType.[0]").toString()
        //System.out.println(JsonPath.read(getStyleDataForSingleStyleIdResponse, "$..data.articleType"));
        Toolbox tools = new Toolbox();

        try {

            String jsonschema = tools.readFileAsString("Data/SchemaSet/JSON/getstyledatasinglestyle.txt");

            //System.out.println("validate(jsondata, jsonschema) = " + validate(getStyleDataForSingleStyleIdResponse, jsonschema));
            Assert.assertTrue(validate(getStyleDataForSingleStyleIdResponse, jsonschema));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public boolean validate(String jsonData, String jsonSchema)
            throws Exception {
        // create the Json nodes for schema and data
        ProcessingReport report = null;
        JsonNode schemaNode = JsonLoader.fromString(jsonSchema); // throws
        // JsonProcessingException
        // if error
        JsonNode data = JsonLoader.fromString(jsonData); // same here

        JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        // load the schema and validate
        JsonSchema schema = factory.getJsonSchema(schemaNode);
        report = schema.validate(data);
		/*
		 * while(report.iterator().hasNext()) {
		 * System.out.println(report.iterator().next().getMessage()); }
		 */
        System.out.println("report = " + report.toString());// report.toString();
        // report.;
        return report.isSuccess();
    }


}
