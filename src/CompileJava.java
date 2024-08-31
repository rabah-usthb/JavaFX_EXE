import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class CompileJava {
static void  CompileSrc(String JavaFX_Path , String JavaFx_Modules,String SrcPath) {
	String scriptPath ="C:\\Users\\DELL\\eclipse-workspace\\JavaFx_Exe\\src\\Compile.bat";
	Scanner sc  = new Scanner(System.in);
	System.out.println("Input Path Bin");
	String BinPath = sc.nextLine();
	BinPath = BinPath.replace("\\", "/");
	sc.close();
	System.out.println(JavaFx_Modules);
	try {
        // Build the command with arguments
		ProcessBuilder processBuilder = new ProcessBuilder(
	            "cmd.exe", "/c",
	            scriptPath,
	            JavaFX_Path,
	            "\""+JavaFx_Modules+"\"",
	            SrcPath,
	            BinPath
	        );

        Process process = processBuilder.start();

        // Print output and error streams for debugging
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
             BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("OUTPUT: " + line);
            }
            while ((line = errorReader.readLine()) != null) {
                System.err.println("ERROR: " + line);
            }
        }

        // Wait for the process to complete
        int exitCode = process.waitFor();
        System.out.println("Batch script finished with exit code: " + exitCode);
    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
    }
}

}
