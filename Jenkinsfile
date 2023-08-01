#!/usr/bin/env groovy

node {
    ["LT_USERNAME=anubhas",
    "LT_ACCESS_KEY=OmOOOshGyRoUgkriuFPDqahTb1fHdRHGXFPyStZb3BEXIQi1hg"]{


   stage('setup') { 

      // Get some code from a GitHub repository
    try{
      git 'https://github.com/Anubha1998/ExtentReport.git'

    }
    catch (err){
      echo err
   }

   }
   stage('build') {
      // Installing Dependencies
      sh 'mvn install'
    }

   stage('test') {
          try{
          sh 'mvn test -D suite=single.xml'
          }
          catch (err){
          echo err
          }  
   }
   stage('end') {  
     echo "Success" 
     }
 }
}
