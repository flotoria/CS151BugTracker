package application.string_converter;

import application.java_beans.ProjectBean;
import javafx.util.StringConverter;

public class ProjectBeanStringConverter extends StringConverter<ProjectBean> {
	 @Override
    public String toString(ProjectBean object) {
        return object != null ? object.getProjectName() : null;
    }

	@Override
	public ProjectBean fromString(String string) {
		return null;
	}
}
