EasyFill
=========

A hackathon idea for hackathon conducted by WnCC, IIT Bombay in association with GDG amd Facebook

An app which automates registration form filling process at IIT club events

A usual trend in orientation conducted at IITB is passing around laptop and filling the information of
the students interested which is a routine and time consuming process.
Enter EasyFill.

Students can install it on their android phone. It asks for details on first use ( Name, Phone, Hostel, etc.).
During an event, you can put either a google form link or an event ID obtained from our server.
In case of google form, the app auto-fills the data and displays th form, requiring the user to only submit the form.
In case of an event registered on the server, data is directly sent and stored on the server, with no user interaction after entering event id and clicking submit

Contributors
============

[Meet](https://github.com/udiboy1209)<br/>
[Spriha](https://github.com/sprihabiswas)<br/>
[Sumith](https://github.com/Sumith1896)<br/>

Dependencies
=============

 * Android 2.3.3+ ( API 10+ )
 * Flask v0.10.1
 * Python 2.7+ server

How to setup server
==================

A server that runs python is necessary.<br/>
Dependence `Flask:v0.10.1`<br/>
1. Install flask.<br/>
2. Run `$python app.py` to set it up in localhost.<br/>
3. The organizer of event can retrieve the data using the event name and password or<br/>
4. Create new event.<br/>



