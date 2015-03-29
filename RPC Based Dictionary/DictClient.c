#include <stdio.h>
#include "dict.h"
void fetch(char inputBuf[]); 
main(int argc, char **argv)
{
	CLIENT *cl;
	dict_in in;
	dict_out *out;
	char* lookupTxt = "lookup";
	char* insertTxt = "insert";
	char* deleteTxt = "delete";
	char* exitTxt = "exit";
	int i=0;
	char input[50];
	char meaningInp[100];
	char* word = "";
	while(1==1){
		printf("\nEnter the procedure  followed by word. There are four options shown below:for ex: \nlookup transform/insert transform/delete transform/exit\n");
		fetch(input);
		//printf("\nString value is %s\n",input);
		char* word = input+7;
		in.key = word;
		if(strncmp(input,lookupTxt,6)==0){
			cl = clnt_create (argv[1], DICT_PROG, DICT_VERS, "tcp");
			if ((out = dictproc_1(&in, cl)) == NULL)
			{ printf ("\nError\n"); exit(1); }
			printf ("Meaning is:%s\n", out -> meaning);
		}else if(strncmp(input,insertTxt,6)==0){
			//printf("\n It is an insert operation\n");
			printf("\nEnter the meaning of the word %s\n",word);
			fetch(meaningInp);
			in.meaning = meaningInp;
			cl = clnt_create (argv[1], DICT_ADD, DICT_ADD_VERS, "tcp");
			if ((out = dictadd_1(&in, cl)) == NULL)
			{ printf ("\nError\n"); exit(1); }
			printf ("Successfully added\n");
		}else if(strncmp(input,deleteTxt,6)==0){
			//printf("\n It is an deleteTxt operation\n");
			cl = clnt_create (argv[1], DICT_DELETE, DICT_DELETE_VERS, "tcp");
			if ((out = dictdelete_1(&in, cl)) == NULL)
			{ printf ("\nError\n"); exit(1); }
			printf("Delete Successfull \n");
		}else if(strncmp(input,exitTxt,4)==0){
			//printf("\n It is an exit operation\n");
			return 0;
		}
	}
	return 0;
}

/* fetch: Store input from user */
void fetch(char inputBuf[])
{
	int c, i;
	int max = 50;
	i = 0;
	while((c = getchar()) != '\n')
	{
		if(i >= max)
			break;
		inputBuf[i++] = c;
	}
	inputBuf[i] = '\0';
}