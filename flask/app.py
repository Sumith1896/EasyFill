# import the Flask class from the flask module
from flask import Flask, render_template, redirect, \
	url_for, request, session, flash, g
from functools import wraps
import sqlite3

# create the application object
app = Flask(__name__)

# config
app.secret_key = 'my precious'
app.database = 'easyfill.db'


# login required decorator
def login_required(f):
	@wraps(f)
	def wrap(*args, **kwargs):
		if 'logged_in' in session:
			return f(*args, **kwargs)
		else:
			flash('You need to login first.')
			return redirect(url_for('login'))
	return wrap


# use decorators to link the function to a url
@app.route('/', methods=['GET', 'POST'])
#@login_required
def login():
	error = None
	if request.method == 'POST':
		g.db = connect_db()
		cur = g.db.execute('select * from events where name = ?', (request.form['username'],))
		event = [dict(name=row[0], passwd=row[1]) for row in cur.fetchall()]
		for post in event:
			if request.form['password'] == post['passwd']:
				session['logged_in'] = True
				flash('You were logged in.')
				curr = g.db.execute('select * from reg where eventname = ?',(request.form['username'],))
				posts = [dict(name=row[0], eventname=row[1]) for row in curr.fetchall()]
				g.db.close()
				return render_template('index.html', posts=posts)  # render a template
		else:
			error = 'Invalid Credentials. Please try again.'
	return render_template('login.html', error=error)


@app.route('/reg/<path:path>')
def register(path):
	if request.method == 'POST':
		g.db = connect_db()
		cur = g.db.execute('INSERT INTO reg VALUES(?,?)',(request.form['username'], path))
		g.db.close()
	return render_template('success.html', error=error)



@app.route('/welcome')
def welcome():
	return render_template('welcome.html')  # render a template


# route for handling the login page logic
#@app.route('/login')

#def login():
#	error = None
#	if request.method == 'POST':
#		g.db = connect_db()
#		cur = g.db.execute('select * from events where name = ?', (request.form['username'],))
#		event = [dict(name=row[0], passwd=row[1]) for row in cur.fetchall()]
#		for post in event:
#			if request.form['password'] == post['passwd']:
#				session['logged_in'] = True
#				flash('You were logged in.')
#				cur = g.db.execute('select name from reg where eventname = ?',(request.form['username'],))
#				posts = [dict(name=row[0], eventname=row[1]) for row in cur.fetchall()]
#				g.db.close()
#				return render_template('index.html', posts=posts)  # render a template
#		else:
#			error = 'Invalid Credentials. Please try again.'
#	return render_template('login.html', error=error)


@app.route('/logout')
@login_required
def logout():
	session.pop('logged_in', None)
	flash('You were logged out.')
	return redirect(url_for('welcome'))

# connect to database
def connect_db():
	return sqlite3.connect(app.database)


# start the server with the 'run()' method
if __name__ == '__main__':
	app.run(debug=True)
