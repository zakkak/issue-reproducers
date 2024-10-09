# Reproducer for native-image issue

Reproducer of https://github.com/oracle/graal/issues/1725

```
cd /tmp
git clone --branch 2024-10-09-oracle-graal-6253-reproducer https://github.com/zakkak/issue-reproducers reproducers
cd reproducers
mvn package
# run in JVM
java \
  -jar target/reproducer-1.0-SNAPSHOT.jar
# generate native-image with --link-at-build-time
native-image \
  --no-fallback \
  --link-at-build-time  \
  -H:+ReportExceptionStackTraces \
  -jar target/reproducer-1.0-SNAPSHOT.jar
# Build fails
```

The error log is:
  
```
Error: Discovered unresolved method during parsing: com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider.<init>(java.lang.String, java.lang.String). This error is reported at image build time because class com.microsoft.sqlserver.jdbc.SQLServerConnection is registered for linking at image build time by command line and command line.
Error encountered while parsing com.microsoft.sqlserver.jdbc.SQLServerConnection.connectInternal(SQLServerConnection.java:2205) 
Parsing context:
   at com.microsoft.sqlserver.jdbc.SQLServerConnection.connect(SQLServerConnection.java:1971)
   at com.microsoft.sqlserver.jdbc.SQLServerDriver.connect(SQLServerDriver.java:1263)
   at root method.(Unknown Source)

Detailed message:

Caused by: com.oracle.graal.pointsto.constraints.UnsupportedFeatureException: Discovered unresolved method during parsing: com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider.<init>(java.lang.String, java.lang.String). This error is reported at image build time because class com.microsoft.sqlserver.jdbc.SQLServerConnection is registered for linking at image build time by command line and command line.
Error encountered while parsing com.microsoft.sqlserver.jdbc.SQLServerConnection.connectInternal(SQLServerConnection.java:2205) 
Parsing context:
   at com.microsoft.sqlserver.jdbc.SQLServerConnection.connect(SQLServerConnection.java:1971)
   at com.microsoft.sqlserver.jdbc.SQLServerDriver.connect(SQLServerDriver.java:1263)
   at root method.(Unknown Source)

Detailed message:

	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.constraints.UnsupportedFeatures.report(UnsupportedFeatures.java:126)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGenerator.runPointsToAnalysis(NativeImageGenerator.java:867)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGenerator.doRun(NativeImageGenerator.java:593)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGenerator.run(NativeImageGenerator.java:555)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGeneratorRunner.buildImage(NativeImageGeneratorRunner.java:544)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGeneratorRunner.build(NativeImageGeneratorRunner.java:731)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGeneratorRunner.start(NativeImageGeneratorRunner.java:151)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGeneratorRunner.main(NativeImageGeneratorRunner.java:99)
Caused by: com.oracle.graal.pointsto.constraints.UnresolvedElementException: Discovered unresolved method during parsing: com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider.<init>(java.lang.String, java.lang.String). This error is reported at image build time because class com.microsoft.sqlserver.jdbc.SQLServerConnection is registered for linking at image build time by command line and command line.
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.SharedGraphBuilderPhase$SharedBytecodeParser.reportUnresolvedElement(SharedGraphBuilderPhase.java:604)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.SharedGraphBuilderPhase$SharedBytecodeParser.reportUnresolvedElement(SharedGraphBuilderPhase.java:598)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.SharedGraphBuilderPhase$SharedBytecodeParser.handleUnresolvedMethod(SharedGraphBuilderPhase.java:525)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.SharedGraphBuilderPhase$SharedBytecodeParser.handleUnresolvedInvoke(SharedGraphBuilderPhase.java:411)
	at jdk.graal.compiler/jdk.graal.compiler.java.BytecodeParser.genInvokeSpecial(BytecodeParser.java:1963)
	at jdk.graal.compiler/jdk.graal.compiler.java.BytecodeParser.genInvokeSpecial(BytecodeParser.java:1953)
	at jdk.graal.compiler/jdk.graal.compiler.java.BytecodeParser.processBytecode(BytecodeParser.java:5806)
	at jdk.graal.compiler/jdk.graal.compiler.java.BytecodeParser.iterateBytecodesForBlock(BytecodeParser.java:3769)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.SharedGraphBuilderPhase$SharedBytecodeParser.iterateBytecodesForBlock(SharedGraphBuilderPhase.java:954)
	at jdk.graal.compiler/jdk.graal.compiler.java.BytecodeParser.handleBytecodeBlock(BytecodeParser.java:3729)
	at jdk.graal.compiler/jdk.graal.compiler.java.BytecodeParser.processBlock(BytecodeParser.java:3576)
	at jdk.graal.compiler/jdk.graal.compiler.java.BytecodeParser.build(BytecodeParser.java:1162)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.SharedGraphBuilderPhase$SharedBytecodeParser.build(SharedGraphBuilderPhase.java:205)
	at jdk.graal.compiler/jdk.graal.compiler.java.BytecodeParser.buildRootMethod(BytecodeParser.java:1054)
	at jdk.graal.compiler/jdk.graal.compiler.java.GraphBuilderPhase$Instance.run(GraphBuilderPhase.java:103)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.SharedGraphBuilderPhase.run(SharedGraphBuilderPhase.java:157)
	at jdk.graal.compiler/jdk.graal.compiler.phases.Phase.run(Phase.java:49)
	at jdk.graal.compiler/jdk.graal.compiler.phases.BasePhase.apply(BasePhase.java:468)
	at jdk.graal.compiler/jdk.graal.compiler.phases.Phase.apply(Phase.java:42)
	at jdk.graal.compiler/jdk.graal.compiler.phases.Phase.apply(Phase.java:38)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.AnalysisParsedGraph.parseBytecode(AnalysisParsedGraph.java:144)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisMethod.lambda$parseGraph$0(AnalysisMethod.java:986)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisMethod.setGraph(AnalysisMethod.java:1002)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisMethod.parseGraph(AnalysisMethod.java:986)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisMethod.ensureGraphParsedHelper(AnalysisMethod.java:958)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisMethod.ensureGraphParsed(AnalysisMethod.java:937)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.phases.InlineBeforeAnalysisGraphDecoder.lookupEncodedGraph(InlineBeforeAnalysisGraphDecoder.java:181)
	at jdk.graal.compiler/jdk.graal.compiler.replacements.PEGraphDecoder.doInline(PEGraphDecoder.java:1215)
	at jdk.graal.compiler/jdk.graal.compiler.replacements.PEGraphDecoder.tryInline(PEGraphDecoder.java:1198)
	at jdk.graal.compiler/jdk.graal.compiler.replacements.PEGraphDecoder.trySimplifyInvoke(PEGraphDecoder.java:1053)
	at jdk.graal.compiler/jdk.graal.compiler.replacements.PEGraphDecoder.handleInvokeWithCallTarget(PEGraphDecoder.java:1005)
	at jdk.graal.compiler/jdk.graal.compiler.replacements.PEGraphDecoder.handleInvoke(PEGraphDecoder.java:991)
	at jdk.graal.compiler/jdk.graal.compiler.nodes.GraphDecoder.processNextNode(GraphDecoder.java:926)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.phases.InlineBeforeAnalysisGraphDecoder.processNextNode(InlineBeforeAnalysisGraphDecoder.java:269)
	at jdk.graal.compiler/jdk.graal.compiler.nodes.GraphDecoder.decode(GraphDecoder.java:654)
	at jdk.graal.compiler/jdk.graal.compiler.replacements.PEGraphDecoder.decode(PEGraphDecoder.java:895)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.phases.InlineBeforeAnalysis.decodeGraph(InlineBeforeAnalysis.java:73)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlowBuilder.parse(MethodTypeFlowBuilder.java:239)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlowBuilder.apply(MethodTypeFlowBuilder.java:712)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlow.createFlowsGraph(MethodTypeFlow.java:167)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlow.ensureFlowsGraphCreated(MethodTypeFlow.java:152)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlow.getOrCreateMethodFlowsGraphInfo(MethodTypeFlow.java:110)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.typestate.DefaultVirtualInvokeTypeFlow.onObservedUpdate(DefaultVirtualInvokeTypeFlow.java:118)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.TypeFlow.update(TypeFlow.java:825)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.PointsToAnalysis$1.run(PointsToAnalysis.java:579)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.util.CompletionExecutor.executeCommand(CompletionExecutor.java:166)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.util.CompletionExecutor.lambda$executeService$0(CompletionExecutor.java:152)
	at java.base/java.util.concurrent.ForkJoinTask$RunnableExecuteAction.compute(ForkJoinTask.java:1735)
	at java.base/java.util.concurrent.ForkJoinTask$RunnableExecuteAction.compute(ForkJoinTask.java:1726)
	at java.base/java.util.concurrent.ForkJoinTask$InterruptibleTask.exec(ForkJoinTask.java:1650)
	at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:507)
	at java.base/java.util.concurrent.ForkJoinPool$WorkQueue.topLevelExec(ForkJoinPool.java:1458)
	at java.base/java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:2034)
	at java.base/java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:191)
Caused by: com.oracle.graal.pointsto.constraints.UnresolvedElementException: Discovered unresolved method during parsing: com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider.<init>(java.lang.String, java.lang.String). This error is reported at image build time because class com.microsoft.sqlserver.jdbc.SQLServerConnection is registered for linking at image build time by command line and command line.
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.SharedGraphBuilderPhase$SharedBytecodeParser.reportUnresolvedElement(SharedGraphBuilderPhase.java:604)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.SharedGraphBuilderPhase$SharedBytecodeParser.reportUnresolvedElement(SharedGraphBuilderPhase.java:598)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.SharedGraphBuilderPhase$SharedBytecodeParser.handleUnresolvedMethod(SharedGraphBuilderPhase.java:525)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.SharedGraphBuilderPhase$SharedBytecodeParser.handleUnresolvedInvoke(SharedGraphBuilderPhase.java:411)
	at jdk.graal.compiler/jdk.graal.compiler.java.BytecodeParser.genInvokeSpecial(BytecodeParser.java:1963)
	at jdk.graal.compiler/jdk.graal.compiler.java.BytecodeParser.genInvokeSpecial(BytecodeParser.java:1953)
	at jdk.graal.compiler/jdk.graal.compiler.java.BytecodeParser.processBytecode(BytecodeParser.java:5806)
	at jdk.graal.compiler/jdk.graal.compiler.java.BytecodeParser.iterateBytecodesForBlock(BytecodeParser.java:3769)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.SharedGraphBuilderPhase$SharedBytecodeParser.iterateBytecodesForBlock(SharedGraphBuilderPhase.java:954)
	at jdk.graal.compiler/jdk.graal.compiler.java.BytecodeParser.handleBytecodeBlock(BytecodeParser.java:3729)
	at jdk.graal.compiler/jdk.graal.compiler.java.BytecodeParser.processBlock(BytecodeParser.java:3576)
	at jdk.graal.compiler/jdk.graal.compiler.java.BytecodeParser.build(BytecodeParser.java:1162)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.SharedGraphBuilderPhase$SharedBytecodeParser.build(SharedGraphBuilderPhase.java:205)
	at jdk.graal.compiler/jdk.graal.compiler.java.BytecodeParser.buildRootMethod(BytecodeParser.java:1054)
	at jdk.graal.compiler/jdk.graal.compiler.java.GraphBuilderPhase$Instance.run(GraphBuilderPhase.java:103)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.SharedGraphBuilderPhase.run(SharedGraphBuilderPhase.java:157)
	at jdk.graal.compiler/jdk.graal.compiler.phases.Phase.run(Phase.java:49)
	at jdk.graal.compiler/jdk.graal.compiler.phases.BasePhase.apply(BasePhase.java:468)
	at jdk.graal.compiler/jdk.graal.compiler.phases.Phase.apply(Phase.java:42)
	at jdk.graal.compiler/jdk.graal.compiler.phases.Phase.apply(Phase.java:38)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.AnalysisParsedGraph.parseBytecode(AnalysisParsedGraph.java:144)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisMethod.lambda$parseGraph$0(AnalysisMethod.java:986)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisMethod.setGraph(AnalysisMethod.java:1002)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisMethod.parseGraph(AnalysisMethod.java:986)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisMethod.ensureGraphParsedHelper(AnalysisMethod.java:958)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisMethod.ensureGraphParsed(AnalysisMethod.java:937)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.phases.InlineBeforeAnalysisGraphDecoder.lookupEncodedGraph(InlineBeforeAnalysisGraphDecoder.java:181)
	at jdk.graal.compiler/jdk.graal.compiler.replacements.PEGraphDecoder.doInline(PEGraphDecoder.java:1215)
	at jdk.graal.compiler/jdk.graal.compiler.replacements.PEGraphDecoder.tryInline(PEGraphDecoder.java:1198)
	at jdk.graal.compiler/jdk.graal.compiler.replacements.PEGraphDecoder.trySimplifyInvoke(PEGraphDecoder.java:1053)
	at jdk.graal.compiler/jdk.graal.compiler.replacements.PEGraphDecoder.handleInvokeWithCallTarget(PEGraphDecoder.java:1005)
	at jdk.graal.compiler/jdk.graal.compiler.replacements.PEGraphDecoder.handleInvoke(PEGraphDecoder.java:991)
	at jdk.graal.compiler/jdk.graal.compiler.nodes.GraphDecoder.processNextNode(GraphDecoder.java:926)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.phases.InlineBeforeAnalysisGraphDecoder.processNextNode(InlineBeforeAnalysisGraphDecoder.java:269)
	at jdk.graal.compiler/jdk.graal.compiler.nodes.GraphDecoder.decode(GraphDecoder.java:654)
	at jdk.graal.compiler/jdk.graal.compiler.replacements.PEGraphDecoder.decode(PEGraphDecoder.java:895)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.phases.InlineBeforeAnalysis.decodeGraph(InlineBeforeAnalysis.java:73)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlowBuilder.parse(MethodTypeFlowBuilder.java:239)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlowBuilder.apply(MethodTypeFlowBuilder.java:712)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlow.createFlowsGraph(MethodTypeFlow.java:167)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlow.ensureFlowsGraphCreated(MethodTypeFlow.java:152)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlow.getOrCreateMethodFlowsGraphInfo(MethodTypeFlow.java:110)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.typestate.DefaultVirtualInvokeTypeFlow.onObservedUpdate(DefaultVirtualInvokeTypeFlow.java:118)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.TypeFlow.update(TypeFlow.java:825)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.PointsToAnalysis$1.run(PointsToAnalysis.java:579)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.util.CompletionExecutor.executeCommand(CompletionExecutor.java:166)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.util.CompletionExecutor.lambda$executeService$0(CompletionExecutor.java:152)
	at java.base/java.util.concurrent.ForkJoinTask$RunnableExecuteAction.compute(ForkJoinTask.java:1735)
	at java.base/java.util.concurrent.ForkJoinTask$RunnableExecuteAction.compute(ForkJoinTask.java:1726)
	at java.base/java.util.concurrent.ForkJoinTask$InterruptibleTask.exec(ForkJoinTask.java:1650)
	at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:507)
	at java.base/java.util.concurrent.ForkJoinPool$WorkQueue.topLevelExec(ForkJoinPool.java:1458)
	at java.base/java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:2034)
	at java.base/java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:191)
Internal exception: com.oracle.svm.core.util.UserError$UserException: Discovered unresolved method during parsing: com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider.<init>(java.lang.String, java.lang.String). This error is reported at image build time because class com.microsoft.sqlserver.jdbc.SQLServerConnection is registered for linking at image build time by command line and command line.
Error encountered while parsing com.microsoft.sqlserver.jdbc.SQLServerConnection.connectInternal(SQLServerConnection.java:2205) 
Parsing context:
   at com.microsoft.sqlserver.jdbc.SQLServerConnection.connect(SQLServerConnection.java:1971)
   at com.microsoft.sqlserver.jdbc.SQLServerDriver.connect(SQLServerDriver.java:1263)
   at root method.(Unknown Source)

Detailed message:

	at org.graalvm.nativeimage.builder/com.oracle.svm.core.util.UserError.abort(UserError.java:97)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.FallbackFeature.reportAsFallback(FallbackFeature.java:248)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGenerator.runPointsToAnalysis(NativeImageGenerator.java:872)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGenerator.doRun(NativeImageGenerator.java:593)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGenerator.run(NativeImageGenerator.java:555)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGeneratorRunner.buildImage(NativeImageGeneratorRunner.java:544)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGeneratorRunner.build(NativeImageGeneratorRunner.java:731)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGeneratorRunner.start(NativeImageGeneratorRunner.java:151)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGeneratorRunner.main(NativeImageGeneratorRunner.java:99)
Caused by: com.oracle.graal.pointsto.constraints.UnsupportedFeatureException: Discovered unresolved method during parsing: com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider.<init>(java.lang.String, java.lang.String). This error is reported at image build time because class com.microsoft.sqlserver.jdbc.SQLServerConnection is registered for linking at image build time by command line and command line.
Error encountered while parsing com.microsoft.sqlserver.jdbc.SQLServerConnection.connectInternal(SQLServerConnection.java:2205) 
Parsing context:
   at com.microsoft.sqlserver.jdbc.SQLServerConnection.connect(SQLServerConnection.java:1971)
   at com.microsoft.sqlserver.jdbc.SQLServerDriver.connect(SQLServerDriver.java:1263)
   at root method.(Unknown Source)

Detailed message:

	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.constraints.UnsupportedFeatures.report(UnsupportedFeatures.java:126)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGenerator.runPointsToAnalysis(NativeImageGenerator.java:867)
	... 6 more
Caused by: com.oracle.graal.pointsto.constraints.UnresolvedElementException: Discovered unresolved method during parsing: com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider.<init>(java.lang.String, java.lang.String). This error is reported at image build time because class com.microsoft.sqlserver.jdbc.SQLServerConnection is registered for linking at image build time by command line and command line.
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.SharedGraphBuilderPhase$SharedBytecodeParser.reportUnresolvedElement(SharedGraphBuilderPhase.java:604)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.SharedGraphBuilderPhase$SharedBytecodeParser.reportUnresolvedElement(SharedGraphBuilderPhase.java:598)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.SharedGraphBuilderPhase$SharedBytecodeParser.handleUnresolvedMethod(SharedGraphBuilderPhase.java:525)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.SharedGraphBuilderPhase$SharedBytecodeParser.handleUnresolvedInvoke(SharedGraphBuilderPhase.java:411)
	at jdk.graal.compiler/jdk.graal.compiler.java.BytecodeParser.genInvokeSpecial(BytecodeParser.java:1963)
	at jdk.graal.compiler/jdk.graal.compiler.java.BytecodeParser.genInvokeSpecial(BytecodeParser.java:1953)
	at jdk.graal.compiler/jdk.graal.compiler.java.BytecodeParser.processBytecode(BytecodeParser.java:5806)
	at jdk.graal.compiler/jdk.graal.compiler.java.BytecodeParser.iterateBytecodesForBlock(BytecodeParser.java:3769)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.SharedGraphBuilderPhase$SharedBytecodeParser.iterateBytecodesForBlock(SharedGraphBuilderPhase.java:954)
	at jdk.graal.compiler/jdk.graal.compiler.java.BytecodeParser.handleBytecodeBlock(BytecodeParser.java:3729)
	at jdk.graal.compiler/jdk.graal.compiler.java.BytecodeParser.processBlock(BytecodeParser.java:3576)
	at jdk.graal.compiler/jdk.graal.compiler.java.BytecodeParser.build(BytecodeParser.java:1162)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.SharedGraphBuilderPhase$SharedBytecodeParser.build(SharedGraphBuilderPhase.java:205)
	at jdk.graal.compiler/jdk.graal.compiler.java.BytecodeParser.buildRootMethod(BytecodeParser.java:1054)
	at jdk.graal.compiler/jdk.graal.compiler.java.GraphBuilderPhase$Instance.run(GraphBuilderPhase.java:103)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.phases.SharedGraphBuilderPhase.run(SharedGraphBuilderPhase.java:157)
	at jdk.graal.compiler/jdk.graal.compiler.phases.Phase.run(Phase.java:49)
	at jdk.graal.compiler/jdk.graal.compiler.phases.BasePhase.apply(BasePhase.java:468)
	at jdk.graal.compiler/jdk.graal.compiler.phases.Phase.apply(Phase.java:42)
	at jdk.graal.compiler/jdk.graal.compiler.phases.Phase.apply(Phase.java:38)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.AnalysisParsedGraph.parseBytecode(AnalysisParsedGraph.java:144)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisMethod.lambda$parseGraph$0(AnalysisMethod.java:986)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisMethod.setGraph(AnalysisMethod.java:1002)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisMethod.parseGraph(AnalysisMethod.java:986)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisMethod.ensureGraphParsedHelper(AnalysisMethod.java:958)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.meta.AnalysisMethod.ensureGraphParsed(AnalysisMethod.java:937)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.phases.InlineBeforeAnalysisGraphDecoder.lookupEncodedGraph(InlineBeforeAnalysisGraphDecoder.java:181)
	at jdk.graal.compiler/jdk.graal.compiler.replacements.PEGraphDecoder.doInline(PEGraphDecoder.java:1215)
	at jdk.graal.compiler/jdk.graal.compiler.replacements.PEGraphDecoder.tryInline(PEGraphDecoder.java:1198)
	at jdk.graal.compiler/jdk.graal.compiler.replacements.PEGraphDecoder.trySimplifyInvoke(PEGraphDecoder.java:1053)
	at jdk.graal.compiler/jdk.graal.compiler.replacements.PEGraphDecoder.handleInvokeWithCallTarget(PEGraphDecoder.java:1005)
	at jdk.graal.compiler/jdk.graal.compiler.replacements.PEGraphDecoder.handleInvoke(PEGraphDecoder.java:991)
	at jdk.graal.compiler/jdk.graal.compiler.nodes.GraphDecoder.processNextNode(GraphDecoder.java:926)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.phases.InlineBeforeAnalysisGraphDecoder.processNextNode(InlineBeforeAnalysisGraphDecoder.java:269)
	at jdk.graal.compiler/jdk.graal.compiler.nodes.GraphDecoder.decode(GraphDecoder.java:654)
	at jdk.graal.compiler/jdk.graal.compiler.replacements.PEGraphDecoder.decode(PEGraphDecoder.java:895)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.phases.InlineBeforeAnalysis.decodeGraph(InlineBeforeAnalysis.java:73)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlowBuilder.parse(MethodTypeFlowBuilder.java:239)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlowBuilder.apply(MethodTypeFlowBuilder.java:712)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlow.createFlowsGraph(MethodTypeFlow.java:167)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlow.ensureFlowsGraphCreated(MethodTypeFlow.java:152)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.MethodTypeFlow.getOrCreateMethodFlowsGraphInfo(MethodTypeFlow.java:110)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.typestate.DefaultVirtualInvokeTypeFlow.onObservedUpdate(DefaultVirtualInvokeTypeFlow.java:118)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.flow.TypeFlow.update(TypeFlow.java:825)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.PointsToAnalysis$1.run(PointsToAnalysis.java:579)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.util.CompletionExecutor.executeCommand(CompletionExecutor.java:166)
	at org.graalvm.nativeimage.pointsto/com.oracle.graal.pointsto.util.CompletionExecutor.lambda$executeService$0(CompletionExecutor.java:152)
	at java.base/java.util.concurrent.ForkJoinTask$RunnableExecuteAction.compute(ForkJoinTask.java:1735)
	at java.base/java.util.concurrent.ForkJoinTask$RunnableExecuteAction.compute(ForkJoinTask.java:1726)
	at java.base/java.util.concurrent.ForkJoinTask$InterruptibleTask.exec(ForkJoinTask.java:1650)
	at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:507)
	at java.base/java.util.concurrent.ForkJoinPool$WorkQueue.topLevelExec(ForkJoinPool.java:1458)
	at java.base/java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:2034)
	at java.base/java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:191)
```