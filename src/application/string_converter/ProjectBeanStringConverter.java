package application.string_converter;

import application.java_beans.ProjectBean;
import javafx.util.StringConverter;

/**
 * Converts a ProjectBean from and to a String
 */
public class ProjectBeanStringConverter extends StringConverter<ProjectBean> {
	/**
	 * Creates a new TicketBean
	 * @param object	the ProjectBean to be converted into a String
	 * @return	the String of the ProjectBean or null if the ProjectBean is null 
	 */
	@Override
    public String toString(ProjectBean object) {
        return object != null ? object.getProjectName() : null;
    }
	
	@Override
	public ProjectBean fromString(String string) {
		return null;
	}
}
