#!/usr/bin/env python3
# -*- coding: utf-8 -*-
#==========================
#  Name:        fortune
#  Author:      Kahsolt
#  Time:        2017/6/7
#  Desciption:  write fortune textdb to mysqldb

# Configurations
DB_MYSQL_HOST = r'kahsolt.cc'
DB_MYSQL_USER = r'wind'
DB_MYSQL_PASSWD = r'chest'
DB_MYSQL_SCHEMA = r'windchest'
DB_MYSQL_CHARSET = r'utf8'

# Imports
import os, sys
import mysql.connector as MySQLdb

# Read
fin=open('literature','r')
fortune=fin.read().split('\n%\n')
fin.close()

# Write
mysql = MySQLdb.connect(host=DB_MYSQL_HOST,
                        user=DB_MYSQL_USER,
                        passwd=DB_MYSQL_PASSWD,
                        db=DB_MYSQL_SCHEMA,
                        charset=DB_MYSQL_CHARSET)
cursor = mysql.cursor()
for f in fortune:
    query = "INSERT INTO fortune(`text`) VALUES('" + f + "');"
    cursor.execute(query)