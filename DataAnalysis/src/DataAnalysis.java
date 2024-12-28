import java.util.Scanner;

public class DataAnalysis {
    public static void main(String[] args) {
        // StringBuilder to store the read data for later reuse
        StringBuilder storestring = new StringBuilder();

        // Scanner to read input from System.in
        Scanner s = new Scanner(System.in);

        // Total row number, initialized to 0
        int n = 0;

        // Variables to read each data item
        String id, grade, score, absence, time, start, sub, quiz, order;

        // Variables to accumulate column data, initialized to 0
        double idsum = 0, gradesum = 0, scoresum = 0, absencesum = 0, timesum = 0, startsum = 0, subsum = 0, quizsum = 0, ordersum = 0;

        // Variables to compute mean (average)
        double idmean, grademean, scoremean, absencemean, timemean, startmean, submean, quizmean, ordermean;

        // Variables to compute standard deviation
        double iddev, gradedev, scoredev, absencedev, timedev, startdev, subdev, quizdev, orderdev;

        // Variable to map Grade to a numeric value
        double gradeNum;

        // Set the delimiter to the Tab character (\t)
        s.useDelimiter("\t");

        // Read the data and store it in the StringBuilder
        while (s.hasNext()) {
            n++;
            id = s.next().trim();
            grade = s.next().trim();
            score = s.next().trim();
            absence = s.next().trim();
            time = s.next().trim();
            start = s.next().trim();
            sub = s.next().trim();
            quiz = s.next().trim();

            // Store the data and the delimiter used
            storestring.append(id);
            storestring.append("\t");
            storestring.append(grade);
            storestring.append("\t");
            storestring.append(score);
            storestring.append("\t");
            storestring.append(absence);
            storestring.append("\t");
            storestring.append(time);
            storestring.append("\t");
            storestring.append(start);
            storestring.append("\t");
            storestring.append(sub);
            storestring.append("\t");
            storestring.append(quiz);
            storestring.append("\t");

            // Change delimiter to read the last item in a row (Order)
            s.useDelimiter("\n");
            order = s.next().trim();

            // Change delimiter back to the Tab character (\t)
            s.useDelimiter("\t");

            // Store the last item (Order) and the delimiter
            storestring.append(order);
            storestring.append("\t");

            if (n == 1) {
                continue; // Ignore the heading
            }

            idsum += Double.parseDouble(id);
            scoresum += Double.parseDouble(score);
            absencesum += Double.parseDouble(absence);
            timesum += Double.parseDouble(time);
            startsum += Double.parseDouble(start);
            subsum += Double.parseDouble(sub);
            quizsum += Double.parseDouble(quiz);
            ordersum += Double.parseDouble(order);

            // Map Grade to a numeric value based on the given rule
            if ("A".equals(grade)) {
                gradeNum = 4;
            } else if ("B".equals(grade)) {
                gradeNum = 3;
            } else if ("C".equals(grade)) {
                gradeNum = 2;
            } else if ("D".equals(grade)) {
                gradeNum = 1;
            } else {
                gradeNum = 0;
            }
            gradesum += gradeNum;
        }

        // Calculate the means
        idmean = idsum / (n - 1);
        scoremean = scoresum / (n - 1);
        absencemean = absencesum / (n - 1);
        timemean = timesum / (n - 1);
        startmean = startsum / (n - 1);
        submean = subsum / (n - 1);
        quizmean = quizsum / (n - 1);
        ordermean = ordersum / (n - 1);

        // Map Grade mean to a numeric value based on the given rule
        if (gradesum / (n - 1) >= 3.5) {
            grademean = 4;
        } else if (gradesum / (n - 1) >= 2.5) {
            grademean = 3;
        } else if (gradesum / (n - 1) >= 1.5) {
            grademean = 2;
        } else if (gradesum / (n - 1) >= 0.5) {
            grademean = 1;
        } else {
            grademean = 0;
        }

        // "Rewind" the data based on the stored string
        s = new Scanner(storestring.toString().trim());

        // Reset the sums back to 0
        idsum = 0;
        gradesum = 0;
        scoresum = 0;
        absencesum = 0;
        timesum = 0;
        startsum = 0;
        subsum = 0;
        quizsum = 0;
        ordersum = 0;

        // Set the delimiter to the Tab character (\t)
        s.useDelimiter("\t");

        // Calculate the standard deviations
        while (s.hasNext()) {
            id = s.next().trim();
            grade = s.next().trim();
            score = s.next().trim();
            absence = s.next().trim();
            time = s.next().trim();
            start = s.next().trim();
            sub = s.next().trim();
            quiz = s.next().trim();

            // Change delimiter to read the last item in a row (Order)
            s.useDelimiter("\n");
            order = s.next().trim();

            // Change delimiter back to the Tab character (\t)
            s.useDelimiter("\t");

            idsum += Math.pow(Double.parseDouble(id) - idmean, 2);

            // Map Grade to a numeric value again
            if ("A".equals(grade)) {
                gradeNum = 4;
            } else if ("B".equals(grade)) {
                gradeNum = 3;
            } else if ("C".equals(grade)) {
                gradeNum = 2;
            } else if ("D".equals(grade)) {
                gradeNum = 1;
            } else {
                gradeNum = 0;
            }
            gradesum += Math.pow(gradeNum - grademean, 2);

            scoresum += Math.pow(Double.parseDouble(score) - scoremean, 2);
            absencesum += Math.pow(Double.parseDouble(absence) - absencemean, 2);
            timesum += Math.pow(Double.parseDouble(time) - timemean, 2);
            startsum += Math.pow(Double.parseDouble(start) - startmean, 2);
            subsum += Math.pow(Double.parseDouble(sub) - submean, 2);
            quizsum += Math.pow(Double.parseDouble(quiz) - quizmean, 2);
            ordersum += Math.pow(Double.parseDouble(order) - ordermean, 2);
        }

        // Calculate the standard deviations
        iddev = Math.sqrt(idsum / (n - 1));
        gradedev = Math.sqrt(gradesum / (n - 1));
        scoredev = Math.sqrt(scoresum / (n - 1));
        absencedev = Math.sqrt(absencesum / (n - 1));
        timedev = Math.sqrt(timesum / (n - 1));
        startdev = Math.sqrt(startsum / (n - 1));
        subdev = Math.sqrt(subsum / (n - 1));
        quizdev = Math.sqrt(quizsum / (n - 1));
        orderdev = Math.sqrt(ordersum / (n - 1));

        // Output the means and standard deviations in the required format
        System.out.println("idmean: " + idmean);
        System.out.println("Grademean: " + grademean);
        System.out.println("Scoremean: " + scoremean);
        System.out.println("Absencemean: " + absencemean);
        System.out.println("Timemean: " + timemean);
        System.out.println("Startmean: " + startmean);
        System.out.println("Submean: " + submean);
        System.out.println("Quizmean: " + quizmean);
        System.out.println("Ordermean: " + ordermean);
        System.out.println("iddev: " + iddev);
        System.out.println("Gradedev: " + gradedev);
        System.out.println("Scoredev: " + scoredev);
        System.out.println("Absencedev: " + absencedev);
        System.out.println("Timedev: " + timedev);
        System.out.println("Startdev: " + startdev);
        System.out.println("Subdev: " + subdev);
        System.out.println("Quizdev: " + quizdev);
        System.out.println("Orderdev: " + orderdev);
    }
}
