import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)))
        {
            String line;
            while ((line = reader.readLine()) != null) {
                // Trim white spaces right away
                line = line.trim();

                // Skip empty lines (tolerating null/whitespace)
                if (line.isEmpty()) {
                    continue;
                }

                // Attempt step 1: parse the target count
                int count;
                try {
                    count = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Expected an integer line count, but found: " + line);
                }

                Point[] points = new Point[count];
                for (int i = 0; i < count; i++) {
                    line = reader.readLine();
                    if (line == null) {
                        throw new IllegalStateException("Unexpected EOF: expected " + count + " lines, but reached end of file at index " + i);
                    }
                    int spaceIdx = line.indexOf(' ');

                    int x = parsePositiveInt(line, 0, spaceIdx);
                    int y = parsePositiveInt(line, spaceIdx + 1, line.length());
                    points[i] = new Point(x, y);

                }

                // Pass array to static solver class
                double minPerimeter = MinimumPerimeterSolver.findMinimumPerimeter(points);
                System.out.println(minPerimeter);           }
        }
        catch (IOException e)
        {
            System.err.println("Error reading Input: " + e.getMessage());
        }

    }
    public static int parsePositiveInt(String s, int start, int end) {
        int num = 0;
        for (int i = start; i < end; i++) {
            num = num * 10 + (s.charAt(i) - '0');
        }
        return num;
    }
}