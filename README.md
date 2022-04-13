Adroit Trading Assignment

**Built Using**
- Jdk 1
- Maven 3.6.3

**Running Application**
```
git clone https://github.com/CoderFromCasterlyRock/Adroit-Trading.git
cd AdroitTrading
mvn clean install -Dmaven.test.skip=true
java -jar target/assignment-1.0.jar
```

**Supported Commands**

Create url mapping: ``` cu www.google.com ```

Create url mapping with shortUrl:``` cu tiny.ly/goog1 www.google.com```

Lookup url mapping:``` lu tiny.ly/goog1```

Delete an existing url mapping: ``` du tiny.ly/goog1```

Get Stats: ``` gs```

Quit Application: ``` qa```


**Stopping Application**
- Ctrl-C from Command Prompt
(Some version of Cygwin/MobaXTerm won't send CTRl-C to the application.)