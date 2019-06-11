API Automation (JAVA JERSEY)
============================

This project contains the automation source code which automates API's for a Github repository creation and basic validation. The automation source is based
on JAVA JERSEY library.


System Requirement:
===================

* IntelliJ IDEA Community Edition (IDE)
* Gradle command line tools
* Java 8 
* TestNG (testing framework)

Pre-requisites:
===============
* Create a token in Github account: Goto https://github.com/settings/tokens -> Generate new token -> Allow delete_repo, repo (all)
* Add token in the 'env.properties' file token field
* and USERNAME in the 'env.properties' file username field

Notes:
======
* Import the project in IDE and
* Then Run the 'RestTest.xml' test file

References:
===========
https://jersey.github.io/documentation/latest/index.html
https://www.geeksforgeeks.org/parse-json-java/
https://developer.github.com/v3/