# CSC460FinalProject

 * ========================================================================================================
 * Course:       
 *               CSC 460 DataBase Design
 * Assignment:   
 *               Final Project - Database-driven Web Application
 * Instructor:   
 *               Dr. Lester I. McCann
 * TAs:          
 *               Anand Vastrad, Theodore Sackos
 * Members:      
 *               XUEHENG WAN
 *               RUYI ZUO
 *               WENKANG ZHOU
 *               YANG HONG
 * --------------------------------------------------------------------------------------------------------
 * Requirements: 1) 
 *                  JavaSE-1.8 (backward compatible)
 *                  or
 *                  JavaSE-1.7 (minimum requirement)
 *
 *               2) User needs to provide username and password for oracle.aloe (built-in)
 *
 *               3) To correctly compile this program on Lectura, please configure:
 *
 *                  3.1) JavaSE-1.8
 *                       export /usr/lib/jvm/java-8-oracle
 *                       or 
 *                       JavaSE-1.7 (optional, if 1.8 is not available)
 *                       export JAVA_HOME=/usr/local/jdk
 *                       
 *                  3.2) Tomcat home directory
 *                       export CATALINA_HOME=/home/hong1/tomcat
 *
 *                  3.3) Testing, should see exported path
 *                       echo $JAVA_HOME
 *                       echo $CATALINA_HOME
 *
 *                  3.4) SSH forwarding (Essential!!!)
 *                       ssh -L <port number>:localhost:<port number> <username>@lectura.cs.arizona.edu
 *
 *               4) Compilation: javac DatabaseController.java
 *
 *               5) Execution: will be excuted by Web Front-End, no need to worry about it
 *
 *               6) Tomcat setup: Correctly configure Tomcat on Lectura
 *                                Please see detailed instructions in HowTo.txt
 *
 *               7) Remove old ROOT folder
 *                  rm -rf ~/Path_To/tomcat/webapps/ROOT
 *
 *               8) Put our ROOT folder in the following directory:
 *                  ~/Path_To/tomcat/webapps/
 *
 *               9) Start Tomcat server
 *                  ~/Path_To/tomcat/bin/startup.sh
 *                  Note:
 *                  it is necessary to re-start the server when you re-compile DatabaseController.java
 *
 *              10) remember to shut down the server when done
 *                  ~/Path_To/tomcat/bin/shutdown.sh
 *
 *              11) Enjoy the most advanced (naive) spacecraft shopping and management system!
 * ========================================================================================================