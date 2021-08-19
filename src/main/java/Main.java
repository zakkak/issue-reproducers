import org.graalvm.home.Version;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Main {

    public static void main(String[] args) {
        if (Version.getCurrent().compareTo(21, 2) < 0) {
            System.out.println("Please use GraalVM >=21.2");
            return;
        }

        try {
            SomeSerializationObject instance = new SomeSerializationObject();
            instance.setPerson(new Person("Sheldon"));
            ExternalizablePerson ep = new ExternalizablePerson();
            ep.setName("Sheldon 2.0");
            instance.setExternalizablePerson(ep);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(instance);
            ByteArrayInputStream bais = new ByteArrayInputStream(out.toByteArray());
            ObjectInputStream is = new ObjectInputStream(bais);
            SomeSerializationObject result = (SomeSerializationObject) is.readObject();
            if (result.getPerson().getName().equals("Sheldon")
                    && result.getExternalizablePerson().getName().equals("Sheldon 2.0")) {
                System.out.println("OK");
            } else {
                System.out.println("Ooops");
            }
        } catch (Exception e) {
            System.out.println("Ooops2");
            e.printStackTrace();
        }
    }
}
