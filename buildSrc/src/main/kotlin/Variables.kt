// Run on IntelliJ
val ideaActive = System.getProperty("idea.active") == "true"

// Run on apple silicon
val isAppleSilicon = System.getProperty("os.arch") == "aarch64"
