/*  CSc 352, Fall 2011, Program #10
 *  Daniel Eisenberg
 *
 *  linkedlist.h -- Header file for linked list.
 *
 *  This file defines constants, data types, and functions for a doubly linked
 *  list data type consisting of ordered pairs of ints. The implementations of
 *  these functions are in the linkedlist.c file.
 */

    /* Operation return codes */

#define OK              0       /* no error */
#define MALLOC_FAILED -11       /* malloc call returned NULL */
#define NO_LIST       -12       /* provided LinkedList pointer was NULL */
#define LIST_EMPTY    -13       /* attempted to access node in an empty list */
#define NULL_POINTER  -14       /* a get function was passed a NULL int ptr */
#define OUT_OF_RANGE  -15       /* get function was passed a bad index */

    /* Node data structure */

typedef struct node* nodeptr;   /* node pointer name */
typedef struct node {
    int x;                      /* first element of ordered pair */
    int y;                      /* second element of ordered pair */
    nodeptr next;               /* pointer to next node in list */
    nodeptr prev;               /* pointer to previous node in list */
} nodetype;                     /* node type name */

    /* Linked list data structure */

typedef struct {
    nodeptr first;              /* the head of the linked list */
    nodeptr last;               /* the tail of the linked list */
} LinkedList;

    /* Linked list function prototypes */

int create_linked_list(LinkedList **);
int destroy_linked_list(LinkedList **);
int add_first(LinkedList *, int, int);
int add_last(LinkedList *, int, int);
int remove_first(LinkedList *);
int remove_last(LinkedList *);
int get_first(LinkedList *, int *, int *);
int get_last(LinkedList *, int *, int *);
int get(LinkedList *, int, int *, int *);
char * linked_list_error_description(int);
