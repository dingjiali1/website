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
} 
