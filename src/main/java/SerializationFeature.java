import com.oracle.svm.core.annotate.*;
import org.graalvm.nativeimage.hosted.Feature;

import org.graalvm.nativeimage.hosted.RuntimeSerialization;

@AutomaticFeature
public class SerializationFeature implements Feature {

    @Override
    public void beforeAnalysis(BeforeAnalysisAccess access) {
        RuntimeSerialization.register(String.class);
        RuntimeSerialization.register(Person.class);
        RuntimeSerialization.register(SomeSerializationObject.class);
        RuntimeSerialization.register(AbstractPerson.class);
        RuntimeSerialization.register(ExternalizablePerson.class);
    }

}