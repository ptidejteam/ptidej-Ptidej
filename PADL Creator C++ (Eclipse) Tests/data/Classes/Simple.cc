class Test;

Test aTest;
int anInt;
int anArrayOfInts[5];
char* aPointerOnChars;

class Test : public testing::Test,
             public viewing::Test {
			 
	class EmbeddedTest {
	}
	
	void foo(Test t) {
	}
}

class PrintingLayoutTextTest : public PrintingLayoutTest {
  typedef PrintingLayoutTest Parent;
 public:
  // Returns if the test is disabled.
  // TODO(maruel):  http://b/1157665 Until the issue is fixed, disable the test
  // if ClearType is enabled.
  static bool IsTestCaseDisabled() {
    return Parent::IsTestCaseDisabled() || IsClearTypeEnabled();
  }
};
