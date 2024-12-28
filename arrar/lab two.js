	The in the following program

public class Person {

   public final int MEN = 0;
   public final int WOMAN = 1;
   private String name;
   private int age;
   boolean adult = false;
   static int numOfPersons = 0;

   public Person(String name, int age) {
       this.name = name;
       this.age = age;
       this.adult = age >=18;
   }


   public String getName() {
       return name;
   }

   public int getAge() {
       return age;
   }

   public void setName(String newName) {
       name = newName;
   }

   public void setAge(int newAge) {
       age = newAge;
       adult = age >=18;
   }
   public static void main(String [] args)
   {
        Person p = new Person("John Smith", 17);
        System.out.println(p.getName());
        p.setAge(18);
        System.out.println(p.getAge());
   }
}/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SeleniumTests/protractor.conf.js to edit this template
 */

exports.config = {
    
    seleniumServerJar: null,
    
    seleniumPort: null,
    
    seleniumArgs: [],
    
    chromeDriver: null,
    
    seleniumAddress: null,
    
    sauceUser: null,
    sauceKey: null,
    
    sauceSeleniumAddress: null,
    
    directConnect: false,
    
    firefoxPath: null,
    
    chromeOnly: false,
    
    exclude: [],
    
    capabilities: {
        browserName: 'chrome',
        
        count: 1,
        
        shardTestFiles: false,
        
        maxInstances: 1,
        
        specs: [],
        
        exclude: []
                
    },
    
    multiCapabilities: [],
    
    maxSessions: -1,
    
    baseUrl: 'http://localhost:9876',
    
    rootElement: 'body',
    
    allScriptsTimeout: 11000,
    
    getPageTimeout: 10000,
    
    beforeLaunch: function () {
    },
    
    onPrepare: function () {
    },
    
    onComplete: function () {
    },
    
    onCleanUp: function (exitCode) {},
    
    afterLaunch: function () {},
    
    params: {
        login: {
            user: 'Jane',
            password: '1234'
        }
    },
    
    resultJsonOutputFile: null,
    
    restartBrowserBetweenTests: false,
    
    framework: 'jasmine',
    
    jasmineNodeOpts: {
        isVerbose: false,
        showColors: true,
        includeStackTrace: true,
        defaultTimeoutInterval: 30000
    },
    
    mochaOpts: {
        ui: 'bdd',
        reporter: 'list'
    },
    
    // Options to be passed to Cucumber.
    cucumberOpts: {
        require: 'cucumber/stepDefinitions.js',
        tags: '@dev',
        format: 'summary'
    }
};
