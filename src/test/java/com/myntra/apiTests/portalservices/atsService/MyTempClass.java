package com.myntra.apiTests.portalservices.atsService;

public class MyTempClass {
	
	Node head;
	
	static class Node{
		
		int data;
		Node next;
		
		Node(int d){
			data =d;
			next = null;
		}
	}

	public static void main(String[] args) {
		
		MyTempClass mtc = new MyTempClass();
		
		mtc.head = new Node(1);
		Node second = new Node(2);
		Node third = new Node(3);
		
		mtc.head.next = second;
		second.next = third;
		
		Node add = new Node(0);
		Node last = new Node(5);
		Node mid = new Node(4);
		addfront(mtc.head, add);
		addlast(add, last);
		insertAfter(add, third, mid);
		delete(add,mid);
		//deletepo(add, 3);
		int k = length(add);
		int n =3;
		//deletefromlast(add, k-n+1);
		//add = reverselist(add);
		//findmiddile(add);
		findcount(add,5);
		print(add);
		
	}

	private static void findcount(Node add, int i) {
		
		Node n = add;
		int count = 0;
		while(n!=null) {
			  if(n.data == i) {
				  count++;
			  }  
			n = n.next;
		}
		System.out.println("count: "+ count);
	}

	private static void findmiddile(Node add) {
		int k = length(add);
		deletefromlast(add, k/2);
	}

	private static Node reverselist(Node node) {
		Node prev = null,current = node, next = null;
		while(current!=null)
		{
			next = current.next;
			current.next = prev;
			prev = current;
			current= next;
		}
		node = prev;
		return node;
	}

	private static void deletefromlast(Node node, int i) {
		Node n = node;
		int count = 0;
		while(n!=null) {
			  if(count == i) {
				  	System.out.println("data: "+ n.data);
				  	break;
			  }  
			n = n.next;
			count++;
		}
		
	}

	private static int length(Node node) {
		Node n = node;
		int count = 0;
		while(n!=null) {  
			n = n.next;
			count++;
		}
		return count;
	}

	private static void deletepo(Node node, int i) {
		Node n = node;
		int count = 0;
		while(n.next!=null) {
			Node m =n;
			  if(count == i-1) {
				  m.next = n.next.next;
				  	break;
			  }  
			n = n.next;
			count++;
		}
	}

	private static void delete(Node head, Node mid) {
		Node n = head;
		while(n.next!=null) {
			Node m =n;
			  if(n.next == mid) {
				  m.next = mid.next;
				  	break;
			  }  
			n = n.next;
		}
	}

	private static void insertAfter(Node head, Node nodeAfter, Node mid) {
		Node n = head;
		while(n.next!=null) {
			  if(n.next == nodeAfter) {
				  mid.next = nodeAfter.next;
				  nodeAfter.next = mid;
				  	break;
			  }  
			n = n.next;
		}
	}

	private static void addlast(Node add, Node last) {
		Node n = add;
		while(n.next!=null) {
			n = n.next;
		}
		n.next = last;
	}

	private static void addfront(Node head, Node add) {
		Node n = head;
		head = add;
		add.next = n;
	}

	private static void print(Node head) {
		
		Node n = head;
		while(n!=null) {
			System.out.println(n.data);
			n= n.next;
		}
	}
}
