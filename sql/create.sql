DECLARE
  id_seq VARCHAR2(255) := 'create sequence id_seq';
  t_customer VARCHAR2(500) := 'create table customer(' ||
                     'id int primary key,' ||
                     'name varchar2(255) unique not null,' ||
                     'birth_date varchar2(255) not null,' ||
                     'phone_num varchar2(255) not null,' ||
                     '"LEVEL" varchar2(255) not null check ("LEVEL" in (''Gold'', ''Silver'', ''Bronze'', ''Normal'')),' ||
                     'purchase_amount int not null)';
  customer_trigger VARCHAR2(255) := 'create trigger customer_trigger ' ||
                                    'before insert on customer ' ||
                                    'for each row ' ||
                                    'begin ' ||
                                    'select id_seq.nextval ' ||
                                    'into :new.id ' ||
                                    'from dual;' ||
                                    'end;';
  t_employee VARCHAR2(500) := 'create table employee(' ||
                              'id int primary key,' ||
                              'name varchar2(255) unique not null,' ||
                              'employee_id varchar2(255) unique not null,' ||
                              'rank varchar2(255) not null check (rank in (''Supervisor'', ''Staff'')))';
  employee_trigger VARCHAR2(255) := 'create trigger employee_trigger ' ||
                                    'before insert on employee ' ||
                                    'for each row ' ||
                                    'begin ' ||
                                    'select id_seq.nextval ' ||
                                    'into :new.id ' ||
                                    'from dual;' ||
                                    'end;';
  t_menu VARCHAR2(500) := 'create table menu(' ||
                          'id int primary key,' ||
                          'name varchar2(255) unique not null,' ||
                          'price int not null)';
  menu_trigger VARCHAR2(255) := 'create trigger menu_trigger ' ||
                                'before insert on menu ' ||
                                'for each row ' ||
                                'begin ' ||
                                'select id_seq.nextval ' ||
                                'into :new.id ' ||
                                'from dual;' ||
                                'end;';
  t_table VARCHAR2(500) := 'create table "TABLE"(' ||
                           'id int primary key, ' ||
                           'table_num int unique not null)';
  table_trigger VARCHAR2(255) := 'create trigger table_trigger ' ||
                                 'before insert on "TABLE" ' ||
                                 'for each row ' ||
                                 'begin ' ||
                                 'select id_seq.nextval ' ||
                                 'into :new.id ' ||
                                 'from dual;' ||
                                 'end;';
  t_order VARCHAR2(500) := 'create table "ORDER"(' ||
                           'id int primary key,' ||
                           'table_id int not null,' ||
                           'menu_id int not null,' ||
                           'constraint order_table_fk foreign key (table_id) references "TABLE" (id),' ||
                           'constraint order_menu_fk foreign key (menu_id) references menu (id))';
  order_trigger VARCHAR2(255) := 'create trigger order_trigger ' ||
                                 'before insert on "ORDER" ' ||
                                 'for each row ' ||
                                 'begin ' ||
                                 'select id_seq.nextval ' ||
                                 'into :new.id ' ||
                                 'from dual;' ||
                                 'end;';
  t_payment VARCHAR2(500) := 'create table payment(' ||
                             'id int primary key,' ||
                             '"DATE" date default sysdate not null,' ||
                             'menu_id int not null,' ||
                             'table_id int not null,' ||
                             'payer_id int,' ||
                             'employee_id int not null,' ||
                             'pay int not null,' ||
                             'constraint payment_menu_fk foreign key (menu_id) references menu (id),' ||
                             'constraint payment_table_fk foreign key (table_id) references "TABLE" (id),' ||
                             'constraint payment_payer_fk foreign key (payer_id) references customer (id),' ||
                             'constraint payment_employee_fk foreign key (employee_id) references employee (id))';
  payment_trigger VARCHAR2(255) := 'create trigger payment_trigger ' ||
                                   'before insert on payment ' ||
                                   'for each row ' ||
                                   'begin ' ||
                                   'select id_seq.nextval ' ||
                                   'into :new.id ' ||
                                   'from dual;' ||
                                   'end;';
BEGIN
  EXECUTE IMMEDIATE id_seq;
  EXECUTE IMMEDIATE t_customer;
  EXECUTE IMMEDIATE customer_trigger;
  EXECUTE IMMEDIATE t_employee;
  EXECUTE IMMEDIATE employee_trigger;
  EXECUTE IMMEDIATE t_menu;
  EXECUTE IMMEDIATE menu_trigger;
  EXECUTE IMMEDIATE t_table;
  EXECUTE IMMEDIATE table_trigger;
  EXECUTE IMMEDIATE t_order;
  EXECUTE IMMEDIATE order_trigger;
  EXECUTE IMMEDIATE t_payment;
  EXECUTE IMMEDIATE payment_trigger;
  EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE = -955 THEN
    NULL;
  ELSE
    RAISE;
  END IF;
END;
