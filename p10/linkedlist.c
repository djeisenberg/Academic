/*=============================================================================
 |   Assignment:  Program #10: "Curses; Worms!"
 |
 |       Author:  Daniel Eisenberg
 |     Language:  GNU C (gcc compiled, tested on lectura)
 |   To Compile:  gcc -c linkedlist.c (if linkedlist.h is in same directory)
 |
 |        Class:  CSc 352 -- Systems Programming and UNIX
 |   Instructor:  Dr. L. McCann
 |     Due Date:  November 10th, 2011, 5:00pm
 |
 +-----------------------------------------------------------------------------
 |
 |  Description:  This file defines and implements a doubly linked list ADT.
 |                The included operations are Create_List, Destroy_List, Add,
 |                Remove, Size, and IsEmpty. The function Error_Description
 |                provides the user with an explanation for a return (error)
 |                code value.
 |
 |                Return value and error reporting are based on Dr. McCann's
 |                arraystack ADT example.
 |
 |        Input:  The list operations do not directly receive input from stdio
 |                or files.
 |
 |       Output:  The list operations do not directly produce output to files,
 |                stdout, or stderr.
 |
 |    Algorithm:  The linked list is a dynamically allocated collection of
 |                Node structs, each of which consists of two integers and a
 |                pointer to a Node.
 |                To create or destroy a linked list, the user must provide the
 |                address of a LinkedList pointer; to perform other operations,
 |                the user must provide the address of a linked list along with
 |                relevant parameters for that operation, as described in
 |                the function header comments below.
 |
 |   Required Features Not Included:  None.
 |
 |   Known Bugs:  None.
 |
 *===========================================================================*/

#include <stdlib.h>
#include "linkedlist.h"

        /*-------------------------------------------- create_linked_list -----
         |  Function create_linked_list
         |
         |  Purpose:  Allocates and initializes an empty linked list.
         |
         |  Parameters:
         |      list (OUT) -- The address of a LinkedList *, which should not
         |                    be pointing to an existing list.
         |
         |  Returns:  OK if the list was successfully created,
         |            NO_LIST if list == NULL, or
         |            MALLOC_FAILED if malloc returns a null pointer.
         *-------------------------------------------------------------------*/

int create_linked_list(LinkedList **list) {
    if (list == NULL) {
        return NO_LIST;
    }

    *list = malloc(sizeof(nodeptr));
    if (*list == NULL) {
        return MALLOC_FAILED;
    }
    /* initialize empty list */
    (*list)->first = (*list)->last = NULL;
    return OK;
}

        /*------------------------------------------- destroy_linked_list -----
         |  Function destroy_linked_list
         |
         |  Purpose:  Deallocates a linked list and sets the pointer to that
         |            list to NULL.
         |
         |  Parameters:
         |      list (IN) -- The address of a pointer to the LinkedList to be
         |              destroyed.
         |
         |  Returns:  OK if the list was successfully destroyed, or
         |            NO_LIST if list == null.
         *-------------------------------------------------------------------*/

int destroy_linked_list(LinkedList **list) {
    if (list == NULL) {
        return NO_LIST;
    }

    /* use first and last to walk and deallocate ("little brother" method) */

    (*list)->last = (*list)->first;             /* initialize to first */
    for (; (*list)->first != NULL; (*list)->last = (*list)->first) {
        (*list)->first = (*list)->last->next;   /* advance first */
        free((*list)->last);                    /* deallocate */
        /* loop update advances last to first */
    }

    /* deallocate linked list */
    free(*list);
    /* set list to NULL */
    list = NULL;
    return OK;
}

        /*----------------------------------------------------- add_first -----
         |  Function add_first
         |
         |  Purpose:  Add an element at the front of the list.
         |
         |  Parameters:
         |      list (IN/OUT) -- A pointer to the linked list to be modified.
         |      x_val (IN) -- The first element of an ordered pair to populate
         |              the new node.
         |      y_val (IN) -- The second element of an ordered pair to populate
         |              the new node.
         |
         |  Returns:  OK if the operation was completed,
         |            NO_LIST if list is NULL, or
         |            MALLOC_FAILED if the malloc call returns a null pointer.
         *-------------------------------------------------------------------*/

int add_first(LinkedList *list, int x_val, int y_val) {
    if (list == NULL) {
        return NO_LIST;
    }
    /* allocate space */
    nodeptr tmp;    /* temporary pointer to new node */
    tmp = malloc(sizeof(nodetype));
    if (tmp == NULL) {
        return MALLOC_FAILED;
    }
    /* populate node */
    tmp->x = x_val;
    tmp->y = y_val;
    /* set node.next to first */
    tmp->next = list->first;
    /* first node */
    tmp->prev = NULL;

    if (list->last == NULL) {
        /* list was empty, update last */
        list->last = tmp;
    } else {
        /* list was not empty, set old firstnode.prev to node */
        list->first->prev = tmp;
    }

    /* set first to new node */
    list->first = tmp;

    return OK;
}

        /*------------------------------------------------------ add_last -----
         |  Function add_last
         |
         |  Purpose:  Adds a new node to the end of a linked list.
         |
         |  Parameters:
         |      list (IN/OUT) -- A pointer to the linked list to be modified.
         |      x_val (IN) -- The first element of an ordered pair to populate
         |              the new node.
         |      y_val (IN) -- The second element of an ordered pair to populate
         |              the new node.
         |
         |  Returns:  OK if the operation was completed,
         |            NO_LIST if list is NULL, or
         |            MALLOC_FAILED if the malloc call returns a null pointer.
         *-------------------------------------------------------------------*/

int add_last(LinkedList *list, int x_val, int y_val) {
    if (list == NULL) {
        return NO_LIST;
    }

    /* allocate new node */
    nodeptr tmp;    /* temporary pointer to new node */
    tmp = malloc(sizeof(nodetype));
    if (tmp == NULL) {
        return MALLOC_FAILED;
    }

    /* populate node */
    tmp->x = x_val;
    tmp->y = y_val;
    /* last node */
    tmp->next = NULL;

    if (list->first == NULL) {
        /* list empty */
        tmp->prev = NULL;
        list->first = list->last = tmp;
    } else {
        /* not empty, connect node and update last */
        list->last->next = tmp;
        tmp->prev = list->last;
        list->last = tmp;
    }

    return OK;
}

        /*-------------------------------------------------- remove_first -----
         |  Function remove_first
         |
         |  Purpose:  Removes the first node of a linked list.
         |
         |  Parameters:
         |      list (IN/OUT) - The list whose first node is to be deleted.
         |
         |  Returns:  OK if the first node was successfully removed,
         |            NO_LIST if list is NULL, or
         |            LIST_EMPTY if list is empty.
         *-------------------------------------------------------------------*/

int remove_first(LinkedList *list) {
    if (list == NULL) {
        return NO_LIST;
    }
    if (list->first == NULL) {
        return LIST_EMPTY;
    }
    nodeptr tmp;    /* temporary pointer used to deallocate node */
    tmp = list->first;
    /* update first */
    list->first = list->first->next;
    /* deallocate */
    free(tmp);
    if (list->first == NULL) {
        /* list had one node, set last to NULL */
        list->last = list->first;
    } else {
        /* new first, set prev to NULL */
        list->first->prev = NULL;
    }
    return OK;
}

        /*--------------------------------------------------- remove_last -----
         |  Function remove_last
         |
         |  Purpose:  Removes the last node of a linked list.
         |
         |  Parameters:
         |      list (IN/OUT) - The list whose last node is to be deleted.
         |
         |  Returns:  OK if the last node was successfully removed,
         |            NO_LIST if list is NULL, or
         |            LIST_EMPTY if list is empty.
         *-------------------------------------------------------------------*/

int remove_last(LinkedList *list) {
    if (list == NULL) {
        return NO_LIST;
    }
    if (list->last == NULL) {
        return LIST_EMPTY;
    }
    nodeptr tmp;    /* temporary pointer used to deallocate node */
    tmp = list->last;
    if (tmp->prev == NULL) {
        /* list has one node, set first, last to NULL */
        list->first = list->last = NULL;
    } else {
        /* move last to previous node */
        list->last = tmp->prev;
        /* set new last node's next to NULL */
        list->last->next = NULL;
    }
    /* deallocate */
    free(tmp);
    return OK;
}

        /*----------------------------------------------------- get_first -----
         |  Function get_first
         |
         |  Purpose:  Stores the ordered pair in the linked list's first node
         |            in the provided addresses.
         |
         |  Parameters:
         |      list (IN) -- A pointer to the linked list to be accessed.
         |      x_ptr (OUT) -- An int pointer to hold the first element of the
         |              ordered pair stored in the first node.
         |      y_ptr (OUT) -- An int pointer to hold the second element of the
         |              ordered pair stored in the first node.
         |
         |  Returns:  OK if the access was successful,
         |            NO_LIST if list is NULL,
         |            LIST_EMPTY if the list is empty, or
         |            NULL_POINTER if either x_ptr or y_ptr is NULL.
         *-------------------------------------------------------------------*/

int get_first(LinkedList *list, int *x_ptr, int *y_ptr) {
    if (list == NULL) {
        return NO_LIST;
    }
    if (x_ptr == NULL || y_ptr == NULL) {
        return NULL_POINTER;
    }
    if (list->first == NULL) {
        return LIST_EMPTY;
    }
    /* list non-empty, dereference pointers and store data */
    *x_ptr = list->first->x;
    *y_ptr = list->first->y;
    return OK;
}

        /*------------------------------------------------------ get_last -----
         |  Function get_last
         |
         |  Purpose:  Stores the ordered pair in the linked list's last node in
         |            the provided addresses.
         |
         |  Parameters:
         |      list (IN) -- A pointer to the linked list to be accessed.
         |      x_ptr (OUT) -- An int pointer to hold the first element of the
         |              ordered pair stored in the last node.
         |      y_ptr (OUT) -- An int pointer to hold the second element of the
         |              ordered pair stored in the last node.
         |
         |  Returns:  OK if the access was successful,
         |            NO_LIST if list is NULL,
         |            LIST_EMPTY if the list is empty, or
         |            NULL_POINTER if either x_ptr or y_ptr is NULL.
         *-------------------------------------------------------------------*/

int get_last(LinkedList *list, int *x_ptr, int *y_ptr) {
    if (list == NULL) {
        return NO_LIST;
    }
    if (x_ptr == NULL || y_ptr == NULL) {
        return NULL_POINTER;
    }
    if (list->last == NULL) {
        return LIST_EMPTY;
    }
    /* list non-empty, dereference pointers and store data */
    *x_ptr = list->last->x;
    *y_ptr = list->last->y;
    return OK;
}

        /*----------------------------------------------------------- get -----
         |  Function get
         |
         |  Purpose:  Stores the ordered pair located in the node at index
         |            (zero-based indexing) in the provided addresses.
         |
         |  Parameters:
         |      list (IN) -- A pointer to the linked list to be accessed.
         |      index (IN) -- The index of the node to be accessed.
         |      x_ptr (OUT) -- An int pointer to hold the first element of the
         |              ordered pair stored in the indicated node.
         |      y_ptr (OUT) -- An int pointer to hold the second element of the
         |              ordered pair stored in the indicated node.
         |
         |  Returns:  OK if the access was successful,
         |            NO_LIST if list is NULL,
         |            LIST_EMPTY if the list is empty,
         |            NULL_POINTER if either x_ptr or y_ptr is NULL, or
         |            OUT_OF_RANGE if no node exists at index.
         *-------------------------------------------------------------------*/

int get(LinkedList *list, int index, int *x_ptr, int *y_ptr) {
    if (list == NULL) {
        return NO_LIST;
    }
    if (x_ptr == NULL || y_ptr == NULL) {
        return NULL_POINTER;
    }
    if (list->first == NULL) {
        return LIST_EMPTY;
    }
    if (index < 0) {
        return OUT_OF_RANGE;
    }
    int i;  /* loop iterator */
    nodeptr tmp; /* temporary pointer used to traverse list */
    /* find index */
    for (i = 0, tmp = list->first; i < index && tmp != NULL; i++) {
        tmp = tmp->next;
    }
    if (tmp == NULL) {
        return OUT_OF_RANGE;
    }
    /* dereference pointers and store data */
    *x_ptr = tmp->x;
    *y_ptr = tmp->y;
    return OK;
}

        /*--------------------------------- linked_list_error_description -----
         |  Function linked_list_error_description
         |
         |  Purpose:  This function provides a verbose description of return
         |            values of operations.
         |
         |  Parameters:
         |      code (IN) -- The value presumably returned by a call to a
         |              linked list operation.
         |
         |  Returns:  A string containing the description of the return code.
         *-------------------------------------------------------------------*/

char *linked_list_error_description(int code) {
    switch (code) {
        case OK:
            return "The operation completed successfully.";
        case MALLOC_FAILED:
            return "Could not allocate memory for the new node.";
        case NO_LIST:
            return "The operation was passed a null LinkedList pointer.";
        case LIST_EMPTY:
            return "Attempted to access an empty list.";
        case NULL_POINTER:
            return "A null int pointer was passed to the operation.";
        case OUT_OF_RANGE:
            return "The index provided to get was negative or too large.";
    }
}
