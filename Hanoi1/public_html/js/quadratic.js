//solve quadratic equation ax^2 + bx +c = 0,here a,b and c are coefficients
function(a, b, c) //
{
    if (a == 0)
    {
        if (b == 0)
        {
            if (c == 0)
            {
                document.write("a=b=c: any x solves the equation");
            }
            else
            {
                alert("a=b=0,c!=0: the equation has no solution");
            }
        }
        else
        {
            document.write("a=0,b!=0:the solution is x=" + (-c/b));
        }
    }
        else //a!=0
        {
           let delta = b*b - 4*a*c;
           if (delta < 0)
           {
               document.write("delta < 0:the equation has no real solution");
           }
           else if(delta == 0)
           {
               document.write("delta = 0:the only solution is x =" + (-b/2/a));
           }
           else //delta > 0
           {
               document.write("delta > 0:two solution x1 =" + ((-b + Math.sqrt(delta))/2/a) + ",x2 = "+((-b - Math.sqrt(delta))/2/a));
           }
       }
   }
   
   
   quadratic(0, 0, 0);
   quadratic(1, 0, 0);
   quadratic(0, 0, 1);
   quadratic(1, 2, 3);
   quadratic(0, 2, 4);
   quadratic(5, 2, 3);
   
   for(let i=0; i < 100; i++)
   {
       quadratic(Math.random(),Math.random(),Math.random());
   }
   
   function monthName2Number(monthNmae)
   {
       let num = 0;
       switch (monthName)
       {
           case "Jan":
           case "jan":
           case "January":
               num = 1;
               break;
           case "Feb":
           case "feb":
           case "Febuary":
               num = 2;
               break;
           case "Mar":
               num = 3;
               break;
           case "Apr":
               num = 4;
               break;
           case "May":
               num = 5;
               break;
           case "June":
               num = 6;
               break;
           case "July":
               num = 7;
               break;
           case "Apr":
               num = 8;
               break;
           case "Sep":
               num = 9;
               break;
           case "Oct":
               num = 10;
               break;
           case "Nov":
               num = 11;
               break;
           case "Dec":
               num = 12;
               break;
           default:
               num = -1;
       }
       return num;
   }
   
   document.writ("Feb is month" + monthName2Number('Feb') + "<br>");
   document.writ("xyz is month" + monthName2Number('xyz') + "<br>");
   
   let mn2d = {"Jan":1, "Feb":2, "Mar":3,
       "Apr":4, "May":5, "June":6,
       "July":7, "Aug":8, "Sep":9,
       "Oct":10, "Nov":11, "Dec":12};
   
   function monthName2Num(monthName)
   {
       let i = mn2d[monthName];
       if (i== null) return -1;
       return i;
   }
   
   /* for (initializations; bool expression; increments)
    * {
    *   statements  // iteration tasks
    *   }
    */
       
   
   
    
   
   
           


