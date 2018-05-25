package com.myntra.apiTests.portalservices.atsService;


public class MyTempClass2 {
		
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
			
			MyTempClass2 mtc = new MyTempClass2();
			
			mtc.head = new Node(1);
			Node second = new Node(2);
			Node third = new Node(3);
			
			mtc.head.next = second;
			second.next = third;
			
			Node five = new Node(5);
			Node five1 = new Node(5);
			Node four = new Node(4);
			Node four1 = new Node(4);
			addlast(mtc.head, four);
			addlast(mtc.head, four1);
			addlast(mtc.head, five);
			addlast(mtc.head, five1);
			removedup(mtc.head);
			//Node head1 = movelastofirst(mtc.head);
			deletealt(mtc.head);
			print(mtc.head);
}
		private static void deletealt(Node head) {
			Node n = head;
			while(n.next!=null) {
				if(n.next.next!=null)
					n.next = n.next.next;
				n = n.next;
			}
			
		}
		private static Node movelastofirst(Node head) {
			Node n = head;
			Node m =null;
			while(n.next!=null) {
				m=n;
				n = n.next;
			}
			m.next = null;
			n.next = head;
			head = n;
			return head;
			
		}
		private static void removedup(Node head) {
			Node n =head;
			while(n.next!=null) {
				if(n.data == n.next.data) {
					n.next = n.next.next;
				}
				else {
					n= n.next;
				}		
			}	
		}
		private static void addlast(Node add, Node last) {
			Node n = add;
			while(n.next!=null) {
				n = n.next;
			}
			n.next = last;
		}
		
		private static void print(Node head) {
			
			Node n = head;
			while(n!=null) {
				System.out.println(n.data);
				n= n.next;
			}
		}
}
