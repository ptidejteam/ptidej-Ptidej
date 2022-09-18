extern
JS_REQUIRES_STACK
void
js_TraceRuntime(JSTracer *trc, JSBool allAtoms) {
	// Nothing
};

class DefaultSlotMap : public SlotMap
{
  public:
    DefaultSlotMap(TraceRecorder& tr) : SlotMap(tr)
    {
    }

    // JS_REQUIRES_STACK JS_ALWAYS_INLINE
    bool visitStackSlots(jsval *vp, size_t count, JSStackFrame* fp)
    {
        for (size_t i = 0; i < count; i++)
            addSlot(&vp[i]);
        return true;
    }
};
