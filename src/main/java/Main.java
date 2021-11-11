
/*
 * Copyright (c) 2019 Matthias Sohn <matthias.sohn@sap.com>
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

public class Main {

	public static void main(String[] args) {
		Object mbean = new Object();
		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		try {
			ObjectName mbeanName = new ObjectName("objectName");
			if (server.isRegistered(mbeanName)) {
				server.unregisterMBean(mbeanName);
			}
			server.registerMBean(mbean, mbeanName);
		} catch (MalformedObjectNameException | InstanceAlreadyExistsException
				| MBeanRegistrationException | NotCompliantMBeanException
				| InstanceNotFoundException e) {
			System.err.println(e.getMessage());
		}
    }
}