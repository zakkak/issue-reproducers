import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

public class Main {

	public static void main(String[] args) {
		try {
			ManagementFactory.getPlatformMBeanServer()
			                 .registerMBean(new Hello(), new ObjectName("test:type=Hello"));
			System.out.println("SUCCESS");
		} catch (MalformedObjectNameException | InstanceAlreadyExistsException
				| MBeanRegistrationException | NotCompliantMBeanException e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
    }
}