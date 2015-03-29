typedef string MyType<255>;  
struct dict_in {
MyType key;
MyType meaning;
};
struct dict_out {
MyType meaning;
};
program DICT_PROG {
version DICT_VERS {
dict_out DICTPROC(dict_in) = 1;
} = 1;
} = 0x13971111;

program DICT_ADD {
version DICT_ADD_VERS {
void DICTADD(dict_in) = 1;
} = 1;
} = 0x13981111;

program DICT_DELETE {
version DICT_DELETE_VERS {
void DICTDELETE(dict_in) = 1;
} = 1;
} = 0x13991111;