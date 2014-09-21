/*=============================================================================
 |   Assignment:  Program #11: The One with the 3D Orthogonal List
 |
 |       Author:  Daniel Eisenberg
 |     Language:  GNU C (gcc compiled, tested on lectura)
 |   To Compile:  gcc -c orthogonallist.c (if orthogonallist.h is in same
 |                  directory)
 |
 |        Class:  CSc 352 -- Systems Programming and UNIX
 |   Instructor:  Dr. L. McCann
 |     Due Date:  December 1st, 2011, 5:00pm
 |
 +-----------------------------------------------------------------------------
 |
 |  Description:  This file defines and implements a 3D orthogonal list ADT.
 |                The included operations are Create_List, Destroy_List,
 |                Insert, Remove, Status, and three Query operations.
 |                The function Error_Description provides the user with an
 |                explanation for a return (error) code value.
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
 |    Algorithm:  The orthogonal list is a dynamically allocated collection of
 |                singly linked lists which index the internal nodes by
 |                various categorical data. The internal nodes are common to
 |                every indexing list.
 |                To create or destroy a list, the user must provide the
 |                address of a pointer to a list; to perform other operations,
 |                the user must provide the address of a list along with
 |                relevant parameters for that operation, as described in
 |                the function header comments below.
 |
 |   Required Features Not Included:  None.
 |
 |   Known Bugs:  None.
 |
 *===========================================================================*/

#include <stdlib.h>
#include <string.h>
#include "orthogonallist.h"

    /* helper function prototypes */

int compar(nodeptr, nodeptr);
void deallocate(nodeptr);
void fix(OrthogonalList *, indexnodeptr, indexnodeptr, nodeptr);

        /*--------------------------------------------------- create_list -----
         |  Function create_list
         |
         |  Purpose:  This function creates and initializes a new orthogonal
         |            list.
         |
         |  Parameters:
         |      list (IN/OUT) -- A pointer to an OrthogonalList pointer, used
         |              to allocate the new list.
         |
         |  Returns:  OK if the list was successfully created,
         |            MALLOC_FAILED if malloc returned a null pointer
         |            NO_LIST if the pointer supplied is NULL.
         *-------------------------------------------------------------------*/

int create_list(OrthogonalList **list) {
    if (list == NULL)
        return NO_LIST;

    *list = malloc(sizeof(OrthogonalList));
    if (*list == NULL)
        return MALLOC_FAILED;

    /* initialize index lists */
    (*list)->years.first = (*list)->majors.first = NULL;
    (*list)->hometowns.first = NULL;

    /* intialize counts */
    (*list)->years_size = (*list)->majors_size = (*list)->hometowns_size = 0; 
    (*list)->node_count = 0;
    return OK;
}

        /*-------------------------------------------------- destroy_list -----
         |  Function destroy_list
         |
         |  Purpose:  This function deallocates an orthogonal list and sets the
         |            pointer to the list to NULL.
         |
         |  Parameters:
         |      list (IN/OUT) -- A pointer to a pointer to an OrthogonalList
         |              which is to be deallocated.
         |
         |  Returns:  OK if the list was successfully deallocated,
         |            NO_LIST if the pointer supplied is NULL or points to a
         |                  null pointer.
         *-------------------------------------------------------------------*/

int destroy_list(OrthogonalList **list) {
    if (list == NULL || *list == NULL)
        return NO_LIST;

    /* handle empty list as special case */
    if ((*list)->node_count == 0) {
        free(*list);
        *list = NULL;
        return OK;
    }

    nodeptr data_temp,          /* traversal pointer for data nodes */
            data_free;          /* traversal pointer for deallocation */
    indexnodeptr index_temp;    /* traversal pointer for indices */

    /* walk through one dimension (years) deallocating all internal nodes */
    index_temp = (*list)->years.first;
    data_temp = index_temp->first;
    data_free = data_temp;

    while (index_temp != NULL) {
        while (data_temp != NULL) {
            data_temp = data_temp->next_by_year;    /* get next node */
            deallocate(data_free);                  /* free previous node */
            data_free = data_temp;                  /* update data_free */
        }
        index_temp = index_temp->next;              /* get next index */
        if (index_temp != NULL) {
            data_temp = index_temp->first;          /* get first node */
            data_free = data_temp;
        }
    }

    /* deallocate years */
    index_temp = (*list)->years.first;
    while (index_temp != NULL) {
        index_temp = index_temp->next;              /* get next index */
        free((*list)->years.first->label);          /* free label */
        free((*list)->years.first);                 /* free previous index */
        (*list)->years.first = index_temp;          /* update first */
    }

    /* deallocate majors */
    index_temp = (*list)->majors.first;
    while (index_temp != NULL) {
        index_temp = index_temp->next;
        free((*list)->majors.first->label);
        free((*list)->majors.first);
        (*list)->majors.first = index_temp;
    }

    /* deallocate hometowns */
    index_temp = (*list)->hometowns.first;
    while (index_temp != NULL) {
        index_temp = index_temp->next;
        free((*list)->hometowns.first->label);
        free((*list)->hometowns.first);
        (*list)->hometowns.first = index_temp;
    }

    /* deallocate list */
    free(*list);
    /* set to NULL */
    *list = NULL;
    return OK;
}

        /*-------------------------------------------------------- insert -----
         |  Function insert
         |
         |  Purpose:  Inserts a new internal node entry into the list, linking
         |            this node to each dimension and creating new indices as
         |            needed.
         |
         |  Parameters:
         |      list (IN/OUT) -- A pointer to the list to be modified.
         |      last (IN) -- The last name for this entry.
         |      first (IN) -- The first name for this entry.
         |      init (IN) -- The middle initial for this entry.
         |      major (IN) -- The major for this entry.
         |      admitted (IN) -- The admission year for this entry.
         |      graduates (IN) -- The graduation year for this entry.
         |      hometown (IN) -- The hometown for this entry.
         |
         |  Returns:  OK if the new entry was successfully added,
         |            NO_LIST if list was a null pointer,
         |            MALLOC_FAILED if malloc returned a null pointer,
         |            DUPLICATE if an exact match for this entry was present.
         *-------------------------------------------------------------------*/

int insert(OrthogonalList *list, char *last, char *first, char init, 
                    char *major, char *admitted, int graduates,
                                                    char *hometown) {
    if (list == NULL)
        return NO_LIST;

    /* make node for new entry */
    nodeptr new_node;
    new_node = calloc(sizeof(nodetype), 1);
    if (new_node == NULL)
        return MALLOC_FAILED;

    /* populate new entry */

    /* get lastname */
    new_node->lastname = malloc(strlen(last)+1);    /* allocate space */
    if (new_node->lastname == NULL) {
        deallocate(new_node);
        return MALLOC_FAILED;
    }
    strcpy(new_node->lastname, last);               /* copy string */

    /* get firstname */
    new_node->firstname = malloc(strlen(first)+1);
    if (new_node->firstname == NULL) {
        deallocate(new_node);
        return MALLOC_FAILED;
    }
    strcpy(new_node->firstname, first);

    /* get middle */
    new_node->middle = init;

    /* get graduation year */
    new_node->graduation_year = graduates;

    indexnodeptr index, /* traversal pointer to find insertion indices */
                 year_index = NULL, /* used to protect structure in event of */
                 major_index = NULL;    /* malloc failure */

    if (list->node_count == 0) {
        /* empty list */

        /* allocate indices */
        list->years.first = malloc(sizeof(indexnodetype));
        if (list->years.first == NULL) {
            deallocate(new_node);
            return MALLOC_FAILED;
        }
        list->majors.first = malloc(sizeof(indexnodetype));
        if (list->majors.first == NULL) {
            deallocate(new_node);
            free(list->years.first);
            return MALLOC_FAILED;
        }
        list->hometowns.first = malloc(sizeof(indexnodetype));
        if (list->hometowns.first == NULL) {
            deallocate(new_node);
            free(list->years.first);
            free(list->majors.first);
            return MALLOC_FAILED;
        }

        /* get labels */
        list->years.first->label = malloc(strlen(admitted)+1);
        list->majors.first->label = malloc(strlen(major)+1);
        list->hometowns.first->label = malloc(strlen(hometown)+1);
        if (list->years.first->label == NULL 
                || list->majors.first->label == NULL
                || list->hometowns.first->label == NULL) {
            deallocate(new_node);
            if (list->years.first->label != NULL)
                free(list->years.first->label);
            if (list->majors.first->label != NULL)
                free(list->majors.first->label);
            if (list->hometowns.first->label != NULL)
                free(list->hometowns.first->label);
            free(list->years.first);
            free(list->majors.first);
            free(list->hometowns.first);
            return MALLOC_FAILED;
        }
        strcpy(list->years.first->label, admitted);
        strcpy(list->majors.first->label, major);
        strcpy(list->hometowns.first->label, hometown);

        /* link and initialize */
        new_node->year = list->years.first;
        new_node->major = list->majors.first;
        new_node->hometown = list->hometowns.first;

        /* point indices at node */
        new_node->year->first = new_node->major->first = new_node;
        new_node->hometown->first = new_node;

        /* no other indices */
        new_node->year->next = new_node->major->next = NULL;
        new_node->hometown->next = NULL;

        /* no other nodes */
        new_node->next_by_year = new_node->next_by_major = NULL;
        new_node->next_by_hometown = NULL;

        /* update sizes */
        list->years_size = list->majors_size = list->hometowns_size = 1;

    } else { /* list not empty */

        int compare;        /* comparison var to find insertion points */

        /* check for duplicate */
        index = list->years.first;
        while (index != NULL) {
            nodeptr temp = index->first;
            compare = compar(new_node, temp);
            while (temp != NULL && compare < 0) {
                temp = temp->next_by_year;
                if (temp != NULL)
                    compare = compar(new_node, temp);
            }
            if (compare == 0) {
                deallocate(new_node);
                return DUPLICATE;
            }
            index = index->next;
        }

        /* find years index */
        index = list->years.first;
        compare = strcmp(index->label, admitted);

        if (compare > 0) { /* new index will be first index */
            /* make new index node */
            index = malloc(sizeof(indexnodetype));
            if (index == NULL) {
                deallocate(new_node);
                return MALLOC_FAILED;
            }

            /* label new index node */
            index->label = malloc(strlen(admitted)+1);
            if (index->label == NULL) {
                free(index);
                deallocate(new_node);
                return MALLOC_FAILED;
            }
            strcpy(index->label, admitted);

            /* attach new index node */
            index->next = list->years.first;
            list->years.first = index;

            /* link new data node */
            index->first = new_node;

            /* no other nodes at this index */
            new_node->next_by_year = NULL;

            /* link data node to index */
            new_node->year = index;

            /* count new index node */
            list->years_size += 1;

            year_index = index;

        } else if (compare == 0) {
            /* insert node at this index */
            nodeptr temp = index->first;    /* traversal pointer */

            /* link node to index */
            new_node->year = index;

            /* find insertion point for data node */
            compare = compar(new_node, temp);
            
            if (compare <= 0) {

                /* insert as first */
                new_node->next_by_year = temp;
                index->first = new_node;

            } else {
                if (temp->next_by_year != NULL)
                    compare = compar(new_node, temp->next_by_year);
                while (temp->next_by_year != NULL && compare > 0) {
                    temp = temp->next_by_year;
                    if (temp->next_by_year != NULL)
                        compare = compar(new_node, temp->next_by_year);
                }
                if (temp->next_by_year == NULL) {
                    /* insert node at end */
                    temp->next_by_year = new_node;
                    new_node->next_by_year = NULL;

                } else {

                    /* insert node */
                    new_node->next_by_year = temp->next_by_year;
                    temp->next_by_year = new_node;
                }
            }
        } else { /* find insertion index */
            if (index->next != NULL)
                compare = strcmp(index->next->label, admitted);
            while (index->next != NULL && compare < 0) {
                index = index->next;
                if (index->next != NULL)
                    compare = strcmp(index->next->label, admitted);
            }
            if (index->next == NULL || compare != 0) { /* no matching index */
                /* make new index node */
                indexnodeptr index_temp = malloc(sizeof(indexnodetype));
                if (index_temp == NULL) {
                    deallocate(new_node);
                    return MALLOC_FAILED;
                }

                /* label new index node */
                index_temp->label = malloc(strlen(admitted)+1);
                if (index_temp->label == NULL) {
                    free(index_temp);
                    deallocate(new_node);
                    return MALLOC_FAILED;
                }
                strcpy(index_temp->label, admitted);

                /* link index node */
                index_temp->next = index->next;
                index->next = index_temp;

                index = index->next;
                /* link data node */
                index->first = new_node;

                /* link data node to index */
                new_node->year = index;

                /* no other nodes at this index */
                new_node->next_by_year = NULL;

                /* count new index node */
                list->years_size += 1;

                year_index = index;

            } else {
                /* insert at this index */
                index = index->next;

                /* link data node to this index */
                new_node->year = index;

                nodeptr temp = index->first;    /* traversal pointer */

                /* find insertion point for data node */
                compare = compar(new_node, temp);

                if (compare <= 0) {

                    /* insert as first */
                    new_node->next_by_year = temp;
                    index->first = new_node;

                } else { /* find insertion point */
                    if (temp->next_by_year != NULL)
                        compare = compar(new_node, temp->next_by_year);
                    while (temp->next_by_year != NULL && compare > 0) {
                        temp = temp->next_by_year;
                        if (temp->next_by_year != NULL)
                            compare = compar(new_node, temp->next_by_year);
                    }
                    if (temp->next_by_year == NULL) {
                        /* insert node at end */
                        temp->next_by_year = new_node;
                        new_node->next_by_year = NULL;

                    } else {

                        /* insert node */
                        new_node->next_by_year = temp->next_by_year;
                        temp->next_by_year = new_node;
                    }
                }
            }
        }

        /* find majors index */
        index = list->majors.first;
        compare = strcmp(index->label, major);

        if (compare > 0) {
            /* make new index node */
            index = malloc(sizeof(indexnodetype));
            if (index == NULL) {
                if (year_index != NULL) {
                    fix(list, year_index, major_index, new_node);
                } else {
                    deallocate(new_node);
                }
                return MALLOC_FAILED;
            }

            /* label new index node */
            index->label = malloc(strlen(major)+1);
            if (index->label == NULL) {
                free(index);
                if (year_index != NULL) {
                    fix(list, year_index, major_index, new_node);
                } else {
                    deallocate(new_node);
                }
                return MALLOC_FAILED;
            }
            strcpy(index->label, major);

            /* attach new index node */
            index->next = list->majors.first;
            list->majors.first = index;

            /* link new data node */
            index->first = new_node;

            /* no other nodes at this index */
            new_node->next_by_major = NULL;

            /* link data node to index */
            new_node->major = index;

            /* count new index node */
            list->majors_size += 1;

            major_index = index;

        } else if (compare == 0) {
            /* insert node at this index */
            nodeptr temp = index->first;    /* traversal pointer */

            /* link node to index */
            new_node->major = index;

            /* find insertion point for data node */
            compare = compar(new_node, temp);
            
            if (compare <= 0) {
                /* insert as first */
                new_node->next_by_major = temp;
                index->first = new_node;

            } else {
                if (temp->next_by_major != NULL)
                    compare = compar(new_node, temp->next_by_major);
                while (temp->next_by_major != NULL && compare > 0) {
                    temp = temp->next_by_major;
                    if (temp->next_by_major != NULL)
                        compare = compar(new_node, temp->next_by_major);
                }
                /* insert node */
                new_node->next_by_major = temp->next_by_major;
                temp->next_by_major = new_node;
            }
        } else {
            if (index->next != NULL)
                compare = strcmp(index->next->label, major);
            while (index->next != NULL && compare < 0) {
                index = index->next;
                if (index->next != NULL)
                    compare = strcmp(index->next->label, major);
            }
            if (index->next == NULL || compare != 0) {
                /* make new index node */
                indexnodeptr index_temp = malloc(sizeof(indexnodetype));
                if (index_temp == NULL) {
                    if (year_index != NULL) {
                        fix(list, year_index, major_index, new_node);
                    } else {
                        deallocate(new_node);
                    }
                    return MALLOC_FAILED;
                }

                /* label index node */
                index_temp->label = malloc(strlen(major)+1);
                if (index_temp->label == NULL) {
                    free(index_temp);
                    if (year_index != NULL) {
                        fix(list, year_index, major_index, new_node);
                    } else {
                        deallocate(new_node);
                    }
                    return MALLOC_FAILED;
                }
                strcpy(index_temp->label, major);

                /* link index node */
                index_temp->next = index->next;
                index->next = index_temp;

                index = index->next;
                /* link data node */
                index->first = new_node;

                /* link data node to index */
                new_node->major = index;

                /* no other nodes at this index */
                new_node->next_by_major = NULL;

                /* count new index node */
                list->majors_size += 1;

                major_index = index;

            } else {
                /* insert at this index */
                index = index->next;

                /* link data node to this index */
                new_node->major = index;

                nodeptr temp = index->first;    /* traversal pointer */

                /* find insertion point for data node */
                compare = compar(new_node, temp);

                if (compare <= 0) {
                    /* insert as first */
                    new_node->next_by_major = temp;
                    index->first = new_node;

                } else {
                    if (temp->next_by_major != NULL)
                        compare = compar(new_node, temp->next_by_major);
                    while (temp->next_by_major != NULL && compare > 0) {
                        temp = temp->next_by_major;
                        if (temp->next_by_major != NULL)
                            compare = compar(new_node, temp->next_by_major);
                    }
                    /* insert node */
                    new_node->next_by_major = temp->next_by_major;
                    temp->next_by_major = new_node;
                }
            }
        }

        /* find hometown index */
        index = list->hometowns.first;
        compare = strcmp(index->label, hometown);

        if (compare > 0) {
            /* make new index node */
            index = malloc(sizeof(indexnodetype));
            if (index == NULL) {
                if (year_index != NULL || major_index != NULL) {
                    fix(list, year_index, major_index, new_node);
                } else {
                    deallocate(new_node);
                }
                return MALLOC_FAILED;
            }

            /* label index node */
            index->label = malloc(strlen(hometown)+1);
            if (index->label == NULL) {
                free(index);
                if (year_index != NULL || major_index != NULL) {
                    fix(list, year_index, major_index, new_node);
                } else {
                    deallocate(new_node);
                }
                return MALLOC_FAILED;
            }
            strcpy(index->label, hometown);

            /* attach new index node */
            index->next = list->hometowns.first;
            list->hometowns.first = index;

            /* link new data node */
            index->first = new_node;

            /* no other nodes at this index */
            new_node->next_by_hometown = NULL;

            /* link data node to index */
            new_node->hometown = index;

            /* count new index node */
            list->hometowns_size += 1;

        } else if (compare == 0) {
            /* insert node at this index */
            nodeptr temp = index->first;    /* traversal pointer */

            /* link node to index */
            new_node->hometown = index;

            /* find insertion point for data node */
            compare = temp->graduation_year - new_node->graduation_year;
            
            if (compare <= 0) {
                /* insert as first */
                new_node->next_by_hometown = temp;
                index->first = new_node;

            } else {
                if (temp->next_by_hometown != NULL)
                    compare = temp->next_by_hometown->graduation_year
                                        - new_node->graduation_year;
                while (temp->next_by_hometown != NULL && compare > 0) {
                    temp = temp->next_by_hometown;
                    if (temp->next_by_hometown != NULL)
                        compare = temp->next_by_hometown->graduation_year
                                    - new_node->graduation_year;
                }
                if (temp->next_by_hometown == NULL) {
                    /* insert node at end */
                    temp->next_by_hometown = new_node;
                    new_node->next_by_hometown = NULL;

                } else {
                    /* insert node */
                    new_node->next_by_hometown = temp->next_by_hometown;
                    temp->next_by_hometown = new_node;
                }
            }
        } else {
            if (index->next != NULL)
                compare = strcmp(index->next->label, hometown);
            while (index->next != NULL && compare < 0) {
                index = index->next;
                if (index->next != NULL)
                    compare = strcmp(index->next->label, hometown);
            }
            if (index->next == NULL || compare != 0) {
                /* make new index node */
                indexnodeptr index_temp = malloc(sizeof(indexnodetype));
                if (index_temp == NULL) {
                    if (year_index != NULL || major_index != NULL)
                        fix(list, year_index, major_index, new_node);
                    else
                        deallocate(new_node);
                    return MALLOC_FAILED;
                }

                /* label index node */
                index_temp->label = malloc(strlen(hometown)+1);
                if (index_temp->label == NULL) {
                    free(index_temp);
                    if (year_index != NULL || major_index != NULL)
                        fix(list, year_index, major_index, new_node);
                    else
                        deallocate(new_node);
                    return MALLOC_FAILED;
                }
                strcpy(index_temp->label, hometown);

                /* link index node */
                index_temp->next = index->next;
                index->next = index_temp;

                index = index->next;
                /* link data node */
                index->first = new_node;

                /* link data node to index */
                new_node->hometown = index;

                /* no other nodes at this index */
                new_node->next_by_hometown = NULL;

                /* count new index node */
                list->hometowns_size += 1;

            } else {
                /* insert at this index */
                index = index->next;

                /* link data node to this index */
                new_node->hometown = index;

                nodeptr temp = index->first;    /* traversal pointer */

                /* find insertion point for data node */
                compare = temp->graduation_year - new_node->graduation_year;

                if (compare <= 0) {
                    /* insert as first */
                    new_node->next_by_hometown = temp;
                    index->first = new_node;

                } else {
                    if (temp->next_by_hometown != NULL)
                        compare = temp->next_by_hometown->graduation_year
                                    - new_node->graduation_year;
                    while (temp->next_by_hometown != NULL && compare > 0) {
                        temp = temp->next_by_hometown;
                        if (temp->next_by_hometown != NULL)
                            compare = temp->next_by_hometown->graduation_year,
                                        - new_node->graduation_year;
                    }
                    /* insert node */
                    new_node->next_by_hometown = temp->next_by_hometown;
                    temp->next_by_hometown = new_node;
                }
            }
        }

    }

    /* count new data node */
    list->node_count += 1;
    return OK;
}

        /*-------------------------------------------------------- compar -----
         |  Function compar
         |
         |  Purpose:  Custom comparison function for nodes by names. Returns
         |            values in the same format as strcmp, with lastname taking
         |            precendence to firstname, and firstname taking precedence
         |            to middle.
         |
         |  Parameters:
         |      node_a (IN) -- The first node to be compared.
         |      node_b (IN) -- The second node to be compared.
         |
         |  Returns:  A value less than, equal to, or greater than, zero
         |            dependent upon the relative natural ordering of names.
         *-------------------------------------------------------------------*/

int compar(nodeptr node_a, nodeptr node_b) {
    if (node_a == NULL || node_b == NULL || node_a->firstname == NULL
                || node_b->firstname == NULL || node_a->lastname == NULL)
        return 0;
    int value;  /* stores comparison results */
    value = strcmp(node_a->lastname, node_b->lastname);
    if (value)
        return value;
    value = strcmp(node_a->firstname, node_b->firstname);
    if (value)
        return value;
    return node_a->middle - node_b->middle;
}

        /*----------------------------------------------------------- fix -----
         |  Function fix
         |
         |  Purpose:  Restores a corrupted list in the event of a malloc
         |            failure during a partially completed insert operation.
         |            This function deallocates its node arguments and restores
         |            years_size and majors_size fields if necessary.
         |            Note that node_count should not have been incremented
         |            before this function was called.
         |
         |  Parameters:
         |      list (IN/OUT) -- A pointer to the list to be fixed.
         |      year (IN) -- A pointer to the year index node which needs to be
         |              removed, or a null pointer if no year index node need
         |              be removed.
         |      major (IN) -- A pointer to the major index node which needs to
         |              be removed, or a null pointer if no major index node
         |              need be removed.
         |      data (IN) -- A pointer to the data node which needs to be
         |              removed from the year and/or major lists.
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/

void fix(OrthogonalList *list, indexnodeptr year, indexnodeptr major, 
                                                            nodeptr data) {
    if (list == NULL || data == NULL)
        return;

    /* remove data from lists as needed */

    if (data->year != NULL && list->years.first == data->year) {
        if (year != NULL) { /* year was a new node with one entry, remove it */
            list->years.first = list->years.first->next;
        } else { /* year index node has other entries */
            if (list->years.first->first == data) {
                list->years.first->first = data->next_by_year;
            } else { /* find data node */
                nodeptr temp = list->years.first->first;
                while (temp->next_by_year != NULL && temp->next_by_year != data)
                    temp = temp->next_by_year;
                if (temp->next_by_year != NULL) /* remove data node */
                    temp->next_by_year = temp->next_by_year->next_by_year;
            }
        }
    } else if (data->year != NULL) {
        indexnodeptr traverse = list->years.first;
        while (traverse->next != NULL && traverse->next != data->year)
            traverse = traverse->next;
        if (traverse->next != NULL) {
            if (year != NULL) { /* year had one entry, remove it */
                traverse->next = traverse->next->next;
            } else { /* year index node has other entries */
                if (traverse->next->first == data) {
                    traverse->next->first = data->next_by_year;
                } else { /* find data node */
                    nodeptr temp = traverse->next->first;
                    while (temp->next_by_year != NULL 
                                && temp->next_by_year != data)
                        temp = temp->next_by_year;
                    if (temp->next_by_year != NULL) /* remove data node */
                        temp->next_by_year = temp->next_by_year->next_by_year;
                }
            }
        }
    }

    /* again for major if necessary */
    if (data->major != NULL) {
        if (list->majors.first == data->major) {
            if (major != NULL) {
                list->majors.first = list->majors.first->next;
            } else {
                if (list->majors.first->first == data) {
                    list->majors.first->first = data->next_by_major;
                } else {
                    nodeptr temp = list->majors.first->first;
                    while (temp->next_by_major != NULL 
                                && temp->next_by_major != data)
                        temp = temp->next_by_major;
                    if (temp->next_by_major != NULL)
                        temp->next_by_major = data->next_by_major;
                }
            }
        } else {
            indexnodeptr traverse = list->majors.first;
            while (traverse->next != NULL && traverse->next != data->major)
                traverse = traverse->next;
            if (traverse->next != NULL) {
                if (major != NULL) {
                    traverse->next = traverse->next->next;
                } else {
                    if (traverse->next->first == data) {
                        traverse->next->first = data->next_by_major;
                    } else {
                        nodeptr temp = traverse->next->first;
                        while (temp->next_by_major != NULL
                                    && temp->next_by_major != data)
                            temp = temp->next_by_major;
                        if (temp->next_by_major != NULL)
                            temp->next_by_major = data->next_by_major;
                    }
                }
            }
        }
    }

    /* deallocate nodes */
    if (year->label != NULL)
        free(year->label);
    if (year != NULL) {
        free(year);
        list->years_size -= 1;
    }
    if (major->label != NULL)
        free(major->label);
    if (major != NULL) {
        free(major);
        list->majors_size -= 1;
    }
    deallocate(data);
}

        /*---------------------------------------------------- deallocate -----
         |  Function deallocate
         |
         |  Purpose:  Deallocates a data node including the deallocation of any
         |            internal fields.
         |
         |  Parameters:
         |      node (IN/OUT) -- The node to be deallocated.
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/

void deallocate(nodeptr node) {
    if (node == NULL)
        return;
    if (node->lastname != NULL)
        free(node->lastname);
    if (node->firstname != NULL)
        free(node->firstname);
    free(node);
}

        /*-------------------------------------------------------- remove -----
         |  Function remove
         |
         |  Purpose:  Attemps to remove an internal node from the orthogonal
         |            list, along with any index nodes for which the target
         |            node was the only entry.
         |
         |  Parameters:
         |      list (IN/OUT) -- The list to be modified.
         |      last (IN) -- The last name for the node to be removed.
         |      first (IN) -- The first name for the node to be removed.
         |      init (IN) -- The middle initial for the node to be removed.
         |
         |  Returns:  OK if the target node was successfully found and removed,
         |            NO_LIST if list was a null pointer,
         |            NOT_FOUND if the node was not present.
         *-------------------------------------------------------------------*/

int remove_node(OrthogonalList *list, char *last, char *first, char init) {
    if (list == NULL)
        return NO_LIST;
    if (list->node_count == 0)
        return NOT_FOUND;

    indexnodeptr index = list->years.first; /* index node traversal ptr */
    nodeptr node = index->first,            /* data node traversal ptr */
            target = NULL;                  /* nodeptr for target node */
    int compare;                            /* stores results of comparisons */

    /* traverse years to find target node or determine not present */
    /* handle first index as special case */
    /* handle first data node as special case */
    compare = strcmp(node->lastname, last);

    if (compare >= 0) { /* found matching lastname or none present at index */

        if (compare == 0) { /* same lastname */

            if (strcmp(node->firstname, first) == 0 && node->middle == init) {
                /* target node found */
                target = node;
                if (node->next_by_year == NULL) { /* only node at this index */

                    /* remove index from list */
                    list->years.first = list->years.first->next;
                    list->years_size -= 1;
                    free(index->label);
                    free(index);

                } else { /* not only node */
                    /* replace first node for this index */
                    index->first = node->next_by_year;
                }

            } else { /* full name doesn't match */
                /* check other entries for same last name */
                if (node->next_by_year != NULL)
                    compare = strcmp(node->next_by_year->lastname, last);
                while (node->next_by_year != NULL) {

                    if (compare == 0) { /* same last name */
                        /* check first name */
                        compare = strcmp(node->next_by_year->firstname, first);

                        if (compare == 0) { /* first name matches */
                            compare = node->next_by_year->middle - init;
                            if (compare == 0) { /* same name */
                                target = node->next_by_year; /* found node */
                                break;
                            }
                        }

                    }

                    if (compare > 0) /* entry not present at this index */
                        break;
                    
                    /* update search */
                    node = node->next_by_year;
                    if (node->next_by_year != NULL)
                        compare = strcmp(node->next_by_year->lastname, last);
                }

                if (target != NULL) { /* entry found */
                    /* remove target from this dimension */
                    node->next_by_year = node->next_by_year->next_by_year;
                }
            }
        }
    } else { /* compare result negative, keep looking */
        if (node->next_by_year != NULL)
            compare = strcmp(node->next_by_year->lastname, last);
        while (node->next_by_year != NULL && compare <= 0) {

            if (compare == 0) { /* last name matches */
                /* check first name */
                compare = strcmp(node->next_by_year->firstname, first);
                if (compare == 0) { /* first name matches */
                    compare = node->next_by_year->middle - init;
                    if (compare == 0) { /* same name */
                        target = node->next_by_year; /* target found */
                        break;
                    }
                }
            }

            if (compare > 0) /* not at this index */
                break;

            /* update search */
            node = node->next_by_year;
            if (node->next_by_year != NULL)
                compare = strcmp(node->next_by_year->lastname, last);
        }

        if (target != NULL) { /* entry found */
            /* remove target from this dimension */
            node->next_by_year = node->next_by_year->next_by_year;
        }
    }

    /* completed search in first index */
    if (target == NULL) { /* traverse indices to find node */
        while (index->next != NULL) {
            node = index->next->first;
            /* check first node */
            compare = strcmp(node->lastname, last);
            if (compare == 0) { /* last names match */
                /* compare first names */
                compare = strcmp(node->firstname, first);
                if (compare == 0) { /* first names match */
                    compare = node->middle - init;
                    if (compare == 0) { /* same name */
                        target = node;  /* target found */
                        break;
                    }
                }
            }

            /* check other nodes */
            if (node->next_by_year != NULL) {
                compare = strcmp(node->next_by_year->lastname, last);
            }
            while (node->next_by_year != NULL && compare <= 0) {

                if (compare == 0) { /* last names match */
                    /* check first names */
                    compare = strcmp(node->next_by_year->firstname, first);
                    if (compare == 0) { /* first names match */
                        /* compare middle initials */
                        compare = node->next_by_year->middle - init;
                        if (compare == 0) { /* same names */
                            target = node->next_by_year; /* target found */
                            break;
                        }
                    }
                }

                if (compare > 0) /* node not present at this index */
                    break;

                /* update search */
                node = node->next_by_year;
                if (node->next_by_year != NULL) {
                    compare = strcmp(node->next_by_year->lastname, last);
                }
            }
            if (target != NULL)
                break;  /* break early in case index needs to be removed */
            index = index->next;
        }

        if (target != NULL) {
            /* remove node from this dimension */
            node->next_by_year = target->next_by_year;
            if (target->next_by_year == NULL && target == index->next->first) {
                /* target sole entry for this index, remove index */
                indexnodeptr temp = index->next;    /* for deallocation */
                index->next = temp->next;   /* remove from index list */

                /* deallocate index */
                free(temp->label);
                free(temp);
                list->years_size -= 1;  /* update index count */
            }
        }
    }

    if (target == NULL) /* target node not present */
        return NOT_FOUND;

    /* remove node from majors dimension */
    index = target->major;
    node = index->first;
    if (node == target) { /* target is first entry for major */
        /* remove node from this dimension */
        index->first = node->next_by_major;
        if (index->first == NULL) { /* sole entry for this index, remove it */

            if (list->majors.first == index) {
                /* remove index from list */
                list->majors.first = list->majors.first->next;
            } else {
                indexnodeptr temp = list->majors.first; /* traversal pointer */
                /* find index */
                while (temp->next != NULL && temp->next != index) {
                    temp = temp->next;
                }
                /* remove index */
                if (temp->next != NULL) {
                    temp->next = temp->next->next;
                }
            }

            /* deallocate index */
            free(index->label);
            free(index);
            list->majors_size -= 1; /* update index count */
        }

    } else { /* target not first entry for this major */
        /* find node */
        while (node->next_by_major != NULL && node->next_by_major != target) {
            node = node->next_by_major;
        }
        if (node->next_by_major != NULL) {
            /* remove node from this dimension */
            node->next_by_major = target->next_by_major;
        }
    }

    /* remove node from hometowns dimension */
    index = target->hometown;
    node = index->first;
    if (node == target) { /* target first entry for hometown */
        /* remove node from this dimension */
        index->first = node->next_by_hometown;
        if (index->first == NULL) { /* sole entry for this index, remove it */

            if (list->hometowns.first == index) {
                /* remove index from this list */
                list->hometowns.first = list->hometowns.first->next;
            } else {
                indexnodeptr temp = list->hometowns.first; /* traversal ptr */
                /* find index */
                while (temp->next != NULL && temp->next != index)
                    temp = temp->next;
                /* remove index */
                if (temp->next != NULL)
                    temp->next = temp->next->next;
            }

            /* deallocate index */
            free(index->label);
            free(index);
            list->hometowns_size -= 1; /* update index count */
        }

    } else { /* target not first entry for this hometown */
        /* find node */
        while (node->next_by_hometown != NULL
                    && node->next_by_hometown != target) {
            node = node->next_by_hometown;
        }
        /* remove node from this dimension */
        if (node->next_by_hometown != NULL)
            node->next_by_hometown = target->next_by_hometown;
    }

    deallocate(target);
    list->node_count -= 1;
    return OK;
}

        /*-------------------------------------------------------- status -----
         |  Function status
         |
         |  Purpose:  Reports the size for each dimension and the total
         |            internal data node count.
         |
         |  Parameters:
         |      list (IN) -- The list whose status is requested.
         |      years (OUT) -- A pointer to hold the years dimension size.
         |      majors (OUT) -- A pointer to hold the majors dimension size.
         |      hometowns (OUT) -- A pointer to hold the hometowns dimension
         |              size.
         |      nodes (OUT) -- A pointer to hold the data node count.
         |
         |  Returns:  OK, or NO_LIST or INVALID_ARGS if null pointers are
         |            supplied.
         *-------------------------------------------------------------------*/

int status(OrthogonalList *list, int *years, int *majors, int *hometowns,
                                                                int *nodes) {
    if (list == NULL)
        return NO_LIST;
    if (years == NULL || majors == NULL || hometowns == NULL || nodes == NULL)
        return INVALID_ARGS;
    *years = list->years_size;
    *majors = list->majors_size;
    *hometowns = list->hometowns_size;
    *nodes = list->node_count;
    return OK;
}

        /*---------------------------------------------------- query_year -----
         |  Function query_year
         |
         |  Purpose:  Reports the number of students admitted between the
         |            range of years provided (inclusive).
         |
         |  Parameters:
         |      list (IN) -- The list to be queried.
         |      begin (IN) -- The first admission year.
         |      end (IN) -- The last admission year.
         |
         |  Returns:  An integer representing the number of students admitted
         |            in the provided range of years, or NO_LIST if list NULL.
         *-------------------------------------------------------------------*/

int query_year(OrthogonalList *list, int begin, int end) {
    if (list == NULL)
        return NO_LIST;
    unsigned int count = 0; /* stores number of students admitted in range */

    int year;               /* want index labels as ints */
    indexnodeptr index = list->years.first; /* index traversal pointer */
    nodeptr node;                           /* node traversal pointer */
    if (index != NULL) {
        node = index->first;
        year = atoi(index->label);  /* get year as int */
    }

    while (index != NULL && year - begin >= 0 && end - year >= 0) {
        /* index in year range */
        /* count all entries for this index */
        while (node != NULL) {
            count++;
            node = node->next_by_year;
        }

        /* get next index */
        index = index->next;
        if (index == NULL)
            break;
        node = index->first;
        year = atoi(index->label);
    }

    return count;
}

        /*--------------------------------------------------- query_major -----
         |  Function query_major
         |
         |  Purpose:  Retrieves a list of students whose major matches the
         |            major parameter. The list is in "phonebook order" by
         |            student name.
         |
         |  Parameters:
         |      list (IN) -- The list to be queried.
         |      major (IN) -- The major to report.
         |
         |  Returns:  A pointer to the index node for the indicated major,
         |            or a null pointer if no students belong to that major.
         *-------------------------------------------------------------------*/

nodeptr query_major(OrthogonalList *list, char *major) {
    if (list == NULL)
        return NULL;
    indexnodeptr result;    /* pointer holds return value for function */
    int compare;            /* stores string comparison results */

    result = list->majors.first;
    if (result != NULL)
        compare = strcmp(result->label, major);
    while (result != NULL && compare < 0) {
        result = result->next;
        if (result != NULL)
            compare = strcmp(result->label, major);
    }

    if (result != NULL && compare == 0)
        return result->first;
    return NULL;
}

        /*------------------------------------------------ query_hometown -----
         |  Function query_hometown
         |
         |  Purpose:  Retrieves a list of students whose hometown matches the
         |            hometown parameter. The list is in "phonebook" order by
         |            student name.
         |
         |  Parameters:
         |      list (IN) -- The list to be queried.
         |      hometown (IN) -- The hometown to report.
         |      year (OUT) -- A pointer to store the last year of graduation.
         |
         |  Returns:  A pointer to the index node for the indicated major,
         |            or a null pointer if no students belong to that major.
         *-------------------------------------------------------------------*/

nodeptr query_hometown(OrthogonalList *list, char *hometown, int *year) {
    if (list == NULL)
        return NULL;
    indexnodeptr result;    /* pointer holds return value for function */
    int compare;            /* stores string comparison results */

    result = list->hometowns.first;
    if (result != NULL)
        compare = strcmp(result->label, hometown);
    while (result != NULL && compare < 0) {
        result = result->next;
        if (result != NULL)
            compare = strcmp(result->label, hometown);
    }

    if (result != NULL && compare == 0) {
        *year = result->first->graduation_year;
        return result->first;
    }
    return NULL;
}

        /*-------------------------------- describe_orthogonal_list_error -----
         |  Function describe_orthogonal_list_error
         |
         |  Purpose:  Returns a human readable explanation of an integer error
         |            code returned by an orthogonal list operation.
         |
         |  Parameters:
         |      code (IN) -- The error code to describe.
         |
         |  Returns:  A string containing an explanation of the supplied code.
         *-------------------------------------------------------------------*/

char *describe_orthogonal_list_error(int code) {
    switch(code) {
        case OK:
            return "No error.";
        case MALLOC_FAILED:
            return "Insufficient memory.";
        case NO_LIST:
            return "No orthogonal list was supplied.";
        case NOT_FOUND:
            return "No record found for that student.";
        case DUPLICATE:
            return "Record exists for that student, new record not entered.";
        case INVALID_ARGS:
            return "Invalid arguments.";
        default:
            return "Undefined code.";
    }
}
