import java.io.File;

public class JavaProjectChecker {
String projectPath;
String srcPath;
String workSpacePath;
int projectStatus; 
	JavaProjectChecker(String InputFilePath){
		this.projectPath = fetch_projectPath(InputFilePath);
		this.srcPath = projectPath+"/src";
		this.workSpacePath = fetch_workSpacePath();
		init_Status();
	}
	
	void init_Status() {
		if(this.projectPath.isBlank()) {
			this.projectStatus = -2;
		}
		else if(this.projectPath.isEmpty()) {
			this.projectStatus = -1;		
		}
		else if(srcEmpty(this.srcPath)) {
			this.projectStatus = 0;
		}
		else {
			this.projectStatus = 1;
		}
	}
	
	String fetch_workSpacePath() {
       if(!projectPath.isBlank() && !projectPath.isEmpty()) {
		return this.projectPath.substring(0,this.projectPath.lastIndexOf("/"));
	}
       else {
    	   return "";
       }
	}
String fetch_projectPath(String InputFilePath){
	String regex = "[a-zA-Z]\\:";
	InputFilePath = InputFilePath.replace("\\", "/");
	if(!pathExist(InputFilePath)) {
		return "   ";
	}
	if(InputFilePath.endsWith("/")) {
		InputFilePath.substring(0, InputFilePath.lastIndexOf("/"));
	}
	while(!InputFilePath.matches(regex)&&InputFilePath.contains("/")) {

		if(pathExist(InputFilePath+"/src")) {
		return InputFilePath;
	}
	else {
		InputFilePath =	InputFilePath.substring(0, InputFilePath.lastIndexOf("/"));
		}	
		
}
	return "";
}
	 boolean pathExist(String Path) {
		File file = new File(Path);
		return file.exists();
	}
	
	
	 boolean srcEmpty(String SrcPath) {
		File file = new File(SrcPath);
		return file.listFiles().length == 0;
	}
	
	
}
