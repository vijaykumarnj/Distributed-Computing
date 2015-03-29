#include "dict.h"
#include <stdio.h>
#include <string.h>
#include <getopt.h>

#define TAB '\t'
#define MAX_BLOCK 20
#define MAXLINE 80
#define TRUE 1
#define FALSE 0

struct _Dictionary
{
	char key[20];
	char value[20];
};

typedef struct _Dictionary Dictionary;

void get_block(char *line, char *cs, const char delimiter);
void print_record(Dictionary *pInfo);
void get_record(char *line, Dictionary *pInfo);
void app(Dictionary *pInfo, FILE *fp);
void fetch(char buffer[]);
void search(Dictionary *pInfo, char search_criteria[],char* meaning);
void print_usage(const char *prog_name);


ict_out *dictproc_1_svc (dict_in *inp, struct svc_req *rqstp)
{
	FILE *fp;
	Dictionary info, *pInfo = &info;
	char buf[80];
	char *mode = "r";
	int ret_val, srch = 0, found_records = 0;
	char* srch_term;
static dict_out *outPtr;
static dict_out out;
outPtr=&out;
printf("Input is %s \n",inp->key);
char* meaningValue="";
srch_term = (char *)malloc(strlen(inp->key));
strcpy(srch_term, inp->key);
printf("the search term is %s",srch_term);
srch_term = inp->key;
	if((fp = fopen("store1", mode)) == NULL)
	{
		fprintf(stderr, "Error: Cannot open file.\n");
		exit(-1);
	}
	/* read in lines until EOF */
	while(fgets(buf, 80, fp))
	{
		get_record(buf, pInfo);
		if(strcmp(pInfo->key, srch_term) == 0)
		{
			print_record(pInfo);
			meaningValue = (char *)malloc(strlen(pInfo->value));
			meaningValue = pInfo->value;
			strcpy(meaningValue, pInfo->value);
			break;
		}
  
	}
	if(meaningValue)
		fprintf(stderr, "Cannot find search criteria!\n");
	else
		printf("Success! record(s) returned.\n"); 
	fclose(fp);
printf("\nSetting the meaning now, %s\n",meaningValue);
outPtr->meaning = (char *)malloc(strlen(meaningValue));
strcpy(outPtr->meaning, meaningValue);
printf("\nResponse message sent is: %s \n",pInfo->value);
printf("\nResponse message sent, meaningValue is: %s \n",meaningValue);
printf("\nServer step 5 \n");
return (outPtr);
printf("\nServer step 6 \n");
}

void *dictadd_1_svc (dict_in *inp, struct svc_req *rqstp)
{
	FILE *fp;
	Dictionary info, *pInfo = &info;
	char buf[80];
	char *mode = "a";
	char line[255];
	printf("Server step 1 \n");
	printf("Input word is :%s \n",inp->key);
	printf("Input meaning is :%s \n",inp->meaning);
	if((fp = fopen("store1", mode)) == NULL)
	{
		fprintf(stderr, "Error: Cannot open file.\n");
		exit(-1);
	}
	strcpy(pInfo->key, inp->key);
	strcpy(pInfo->value, inp->meaning);
	//app(pInfo, fp); 
	char* key =(char *)malloc(strlen(inp->key));
	strcpy(key, inp->key);
	char* value =(char *)malloc(strlen(inp->meaning));
	strcpy(value, inp->meaning);
	strcpy(line, key);
	strcat(line, "\t");
	strcat(line, value);
	strcat(line, "\n\0");
	/* write it to file */
	printf("Line to be added is %s \n",line);
	fputs(line, fp);
	printf("Server step 2 \n");
	fclose(fp);
	printf("\nWord Successfully added \n");

}
void *dictdelete_1_svc (dict_in *inp, struct svc_req *rqstp)
{
	FILE *fptr,*fptrNew;
	char buf[100];
       if((fptr = fopen("store1","r"))==NULL)
                  {
                   printf("Cannot open file\n");
                   exit(1);
                   }
	fptrNew = fopen("store1_tmp","a");
	char* key =(char *)malloc(strlen(inp->key));
	strcpy(key, inp->key);
	int sizeVal = sizeof(inp->key);
	printf("Size value is %d \n",sizeVal);
	while(fgets(buf, 80, fptr))
	{
		int match = 1;int i=0;
		printf("Buf is %s ",buf);
		while(i<sizeVal-1){
			if(key[i]!='\0'&&key[i]!=127&& buf[i]!=key[i]){
				match =0;
				break;
			}
			i++;
		}	
		if(match==0){
			fputs(buf, fptrNew);
		}
	}
	fclose(fptr);fclose(fptrNew);
	rename("store1_tmp", "store1");
	printf("Completed successfully ");
}

/* get_str: Fetch one block of information separated by tabs */
void get_block(char *line, char *cs, const char delimiter)
{
	char buf[MAX_BLOCK];
	static int i = 0;
 
	while(line[i] != '\0')
	{
		if(line[i] == delimiter)
		{
			i++;
			break;
		}
		else if(line[i] == '\n')
		{
			i = 0;
			break;
		}
		*cs++ = line[i++];
	}
	*cs = '\0';
}

/* print_record: Print each text segment out as a record on separate lines */
void print_record(Dictionary *pInfo)
{
	static int j = 1;

	printf("Record No.: %d\n", j++);
	printf("Key: %s\n", pInfo->key);
	printf("Value: %s\n", pInfo->value);
}

/* get_record: Set each text piece into structure counterparts */
void get_record(char *line, Dictionary *pInfo)
{
	get_block(line, pInfo->key, TAB);
	get_block(line, pInfo->value, TAB);
}

/* fetch: Store input from user */
void fetch(char buffer[])
{
	int c, i;
	int max = MAX_BLOCK;

	i = 0;
	while((c = getchar()) != '\n')
	{
		if(i >= max)
			break;
		buffer[i++] = c;
	}
	buffer[i] = '\0';
}

/* append: Write new record to end of file */
void app(Dictionary *pInfo, FILE *fp)
{
	char line[MAXLINE];

	/* construct line from input */
	strcpy(line, pInfo->key);
	strcat(line, "\t");
	strcat(line, pInfo->value);
	strcat(line, "\t");
	strcat(line, "");
	strcat(line, "\t");
	strcat(line, "");
	strcat(line, "\n\0");

	/* write it to file */
	fputs(line, fp);
}

void search(Dictionary *pInfo, char search_criteria[],char* meaning)
{
//	char* meaning = "";
	printf("\nKey is %s",pInfo->key);
	printf("\nCriteria name is %s",search_criteria);
	if(strcmp(pInfo->key, search_criteria) == 0)
	{
		print_record(pInfo);
		meaning = pInfo->value;
	printf("\nCopied value is %s",meaning);
		//return (char*)meaning;
	}	
	return;
}