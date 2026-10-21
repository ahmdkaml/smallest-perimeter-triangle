# Assignment: Smallest Perimeter Triangle

1. Build a runnable jar **that also contains your source code** (a `Main-Class` in the manifest; read stdin, write stdout).
   Compile with Java 21 or older, e.g.

       javac --release 21 Main.java
       jar cfe 20240001.jar Main Main.class Main.java      # list every .class and .java file of your solution

2. Name it `<your_student_id>.jar`, put it in `submissions/`, commit and push.
3. Go to the **Actions** tab -> **Check my submission** -> **Run workflow**, and type the path of your jar
   (e.g. `submissions/20240001.jar`). Leave it empty if `submissions/` holds exactly one jar.
   The result appears in the run's summary page.

A green check means the jar is well-formed, contains its sources and is fast enough on the public inputs.
Grading uses additional hidden tests. The checker itself lives in the course's central repository.
