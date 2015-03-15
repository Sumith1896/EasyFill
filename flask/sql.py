# sql.py - Create a SQLite3 table and populate it with data


import sqlite3

# create a new database if the database doesn't already exist
with sqlite3.connect('easyfill.db') as connection:

    # get a cursor object used to execute SQL commands
    c = connection.cursor()

    # create the table
    #c.execute('CREATE TABLE orgies(name TEXT, roll TEXT, dept TEXT, hostel TEXT, phone TEXT, email TEXT)')

    # insert dummy data into the table
    #c.execute('INSERT INTO orgies VALUES("Sumith", "140050081", "CSE", "H15B", "9167781840", "sumith1896@gmail.com")')
    #c.execute('INSERT INTO orgies VALUES("Meet", "140050081", "CSE", "H15B", "9167781840", "sumith1896@gmail.com")')
    #c.execute('INSERT INTO orgies VALUES("Spriha", "140050081", "CSE", "H15B", "9167781840", "sumith1896@gmail.com")')

    #c.execute('CREATE TABLE events(name TEXT, passwd TEXT)')
    #c.execute('INSERT INTO events VALUES("moodi","moodindigo")')
    #c.execute('INSERT INTO events VALUES("techfest","techfest")')
    #c.execute('CREATE TABLE reg(name TEXT, eventname TEXT)')
    #c.execute('INSERT INTO events VALUES("orgies","orgies")')
    #c.execute('INSERT INTO reg VALUES("sumith","techfest")')
    #c.execute('INSERT INTO reg VALUES("sumith","moodi")')
    #c.execute('INSERT INTO reg VALUES("meet","techfest")')
    #c.execute('INSERT INTO reg VALUES("meet","orgies")')
    #c.execute('INSERT INTO reg VALUES("spriha","moodi")')
    #c.execute('INSERT INTO reg VALUES("spriha","orgies")')
    #c.execute('INSERT INTO events VALUES("ecell","ecell")')
    #c.execute('CREATE TABLE stds(name TEXT, rollno TEXT, hostel TEXT, phone TEXT, email TEXT, eventname TEXT)')
    c.execute('INSERT INTO stds VALUES("Spriha", "144250081", "H15C", "9167534840", "spriha@gmail.com","wncc")')

