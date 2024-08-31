import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class JavaFxChecker {
int FX_Status = 0;
String workSpacePath;
String LauncherPath;
String srcPath;
String ProjectName;
String MainFile;
LinkedList<String> JavaFile = new LinkedList<>();
String JavaFx_Module="";
String JavaFx_Path = "";
 JavaFxChecker(String srcPath,String workSpacePath){
	 this.srcPath = srcPath;
	 this.workSpacePath =workSpacePath;
	 this.ProjectName = getProjectName();
	 this.LauncherPath = getLauncherPath(workSpacePath);
	 File file = new File(srcPath);
	 FillJavaList(file.listFiles());
	 LoopingInLaunchFolder();
	 if(this.FX_Status==1) {
		 CompileJava.CompileSrc(this.JavaFx_Path, this.JavaFx_Module, this.srcPath);
	 }
}
 public String getProjectName() {
	 String project = srcPath.replace(workSpacePath, "");
	 project = project.replace("/src", "");
	 return project.replace("/","");
}
 
  String getLauncherPath(String workSpacePath) {
	return workSpacePath+"/.metadata/.plugins/org.eclipse.debug.core/.launches";
}
  boolean possibleLaunchFile(String FileName) {
	  for(String file : this.JavaFile) {
		String Regex = file+"(\\s\\(\\d+\\))?\\.launch";
		if(FileName.matches(Regex)) {
			return true;
		}
	  }
	  return false;
  }
  
  void ReadFile(File file) {
	  String Line ="";
	  try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
          while ((Line = reader.readLine() )!= null) {
          if(isMainLine(Line)) {
        	  if(!isRightLaunchFile(Line)) {
        		  break;
        	  }
        	  else {
            	  this.MainFile = fetch_MainFile(Line);
              }
          }
          
          else if(IsVmArgLine(Line)) {
        	  this.FX_Status = 1;
        	  this.JavaFx_Module = fetch_ModuleFx(Line);
        	  this.JavaFx_Path = fetch_FxPath(Line);
        	  break;
          }
          }
	  }catch(IOException e) {
		  
	  }
  }
  
  void LoopingInLaunchFolder() {
	  File LaunchFolder = new File(LauncherPath);
	  for ( File file : LaunchFolder.listFiles()) {
	    if(file.isFile()&& possibleLaunchFile(file.getName())) {
		if(this.FX_Status == 0) {
		  ReadFile(file);
		}
		else {
			break;
		}
	}
	}

	return; 
  }
  
  boolean isMainLine(String Line) {
	  String Regex ="\\s*\\<listEntry value=\"(/\\w+)+/(\\w+)\\.java\"/\\>\\s*";
	  return Line.matches(Regex);  
  }
  
  boolean isRightLaunchFile(String Line) {
	  if(Line.indexOf("/")+1 <0 || Line.indexOf("/src/")<0){
		  return false;
	  }
	  else {
	return this.ProjectName.equals(Line.substring((Line.indexOf("/")+1),Line.indexOf("/src/")));
	  }
	  }
  
  String fetch_MainFile(String Line) {
     return Line.substring(Line.indexOf("src/"));
  }
  
  void FillJavaList(File[]srclist) {
	  for(File file : srclist) {
		  if(file.isDirectory()&&file.listFiles().length!=0) {
			  FillJavaList(file.listFiles());
		  }
		  else if (file.isFile()&&file.getName().endsWith(".java")) {
			  this.JavaFile.add(file.getName().replace(".java", ""));
		  }
	  }
  }
  
  boolean IsVmArgLine(String Line) {
	  String Driver ="[a-zA-Z]\\:";
	  String File ="[a-zA-Z\\-_0-9\\.]+";
	  String Path="("+Driver+")(\\\\("+File+"))+";
	  String regex = "\\s*\\<stringAttribute key=\"org.eclipse\\.jdt\\.launching\\.VM_ARGUMENTS\" value=\"--module-path \\&quot;"+Path+"\\&quot; --add-modules javafx\\.\\w+(\\,javafx\\.\\w+)*\"/\\>";
     return Line.matches(regex);
  }
  
  String fetch_FxPath(String Line) {
	  return Line.substring((Line.indexOf("&quot;")+6),(Line.lastIndexOf("&quot;"))).replace("\\", "/");
  }
  
  String fetch_ModuleFx(String Line) {
	return Line.substring((Line.indexOf("--add-modules ")+14) , Line.lastIndexOf("\""));  
  }
}
