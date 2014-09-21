/*  CSc 352, Fall 2011, Program #11
 *  Daniel Eisenberg
 *
 *  orthogonallist.h -- Header file for orthogonal list ADT.
 *
 *  This file defines constants, data types, and functions for a 3D orthogonal
 *  list data type consisting of linked lists. The implementations of
 *  these functions are in the orthogonallist.c file.
 */

    /* Operation return codes */

#define OK              0       /* no error */
#define MALLOC_FAILED -11       /* malloc call returned NULL */
#define NO_LIST       -12       /* provided OrthogonalList pointer was NULL */
#define NOT_FOUND     -13       /* the query does not match any entries */
#define DUPLICATE     -14       /* attempted to add a duplicate entry */
#define INVALID_ARGS  -15       /* invalid arguments supplied */

typedef struct node* nodeptr;
typedef struct inode* indexnodeptr;

    /* Internal node type */

typedef struct node {
    char *lastname;             /* student name */
    char *firstname;            /* name */
    char middle;                /* name */
    int graduation_year;        /* student's expected grad year */
    nodeptr next_by_year;       /* the next student admitted same year */
    nodeptr next_by_major;      /* the next student with this major */
    nodeptr next_by_hometown;   /* the next student with same hometown */
    indexnodeptr year;          /* entry node for this admission year */
    indexnodeptr major;         /* entry node for this major */
    indexnodeptr hometown;      /* entry node for this hometown */
} nodetype;

    /* Indexing entry node type */

typedef struct inode {
    char *label;                /* admission yr, major, or hometown */
    indexnodeptr next;          /* next index for students in this dimension */
    nodeptr first;              /* first student for this index */
} indexnodetype;

    /* Entry point type */

typedef struct {
    indexnodeptr first;         /* entry point to indices for this dimension */
} head;

    /* Orthogonal List type */

typedef struct {
    head years;                 /* admission year index list */
    head majors;                /* majors index list */
    head hometowns;             /* hometowns index list */
    unsigned int years_size;    /* number of unique admission years */
    unsigned int majors_size;   /* number of unique majors */
    unsigned int hometowns_size;/* number of unique hometowns */
    unsigned int node_count;    /* number of internal nodes */
} OrthogonalList;

    /* List operation prototypes */

int create_list(OrthogonalList **);
int destroy_list(OrthogonalList **);
int insert(OrthogonalList *, char*, char*, char, char*, char*, int, char*);
int remove_node(OrthogonalList *, char*, char*, char);
int status(OrthogonalList *, int*, int*, int*, int*);
int query_year(OrthogonalList *, int, int);
nodeptr query_major(OrthogonalList *, char*);
nodeptr query_hometown(OrthogonalList *, char*, int*);
char *describe_orthogonal_list_error(int);
