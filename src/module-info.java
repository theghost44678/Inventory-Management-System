/**
 * 
 */
/**
 * 
 */
module projectOne {
	// ... other requires statements ...
    requires org.junit.jupiter.api;
    requires org.junit.platform.commons;

    // Ensure your packages are exported so JUnit can access them
    exports projectone.contact;
    exports projectone.task;
    exports projectone.appointment;
}