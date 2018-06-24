/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package Service2;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.11.0)", date = "2018-06-24")
public class CalculatorException extends org.apache.thrift.TException implements org.apache.thrift.TBase<CalculatorException, CalculatorException._Fields>, java.io.Serializable, Cloneable, Comparable<CalculatorException> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CalculatorException");

  private static final org.apache.thrift.protocol.TField ERR_FIELD_DESC = new org.apache.thrift.protocol.TField("err", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField WHAT_FIELD_DESC = new org.apache.thrift.protocol.TField("what", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new CalculatorExceptionStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new CalculatorExceptionTupleSchemeFactory();

  public Error err; // required
  public java.lang.String what; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ERR((short)1, "err"),
    WHAT((short)2, "what");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // ERR
          return ERR;
        case 2: // WHAT
          return WHAT;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ERR, new org.apache.thrift.meta_data.FieldMetaData("err", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.ENUM        , "Error")));
    tmpMap.put(_Fields.WHAT, new org.apache.thrift.meta_data.FieldMetaData("what", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CalculatorException.class, metaDataMap);
  }

  public CalculatorException() {
  }

  public CalculatorException(
    Error err,
    java.lang.String what)
  {
    this();
    this.err = err;
    this.what = what;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CalculatorException(CalculatorException other) {
    if (other.isSetErr()) {
      this.err = other.err;
    }
    if (other.isSetWhat()) {
      this.what = other.what;
    }
  }

  public CalculatorException deepCopy() {
    return new CalculatorException(this);
  }

  @Override
  public void clear() {
    this.err = null;
    this.what = null;
  }

  public Error getErr() {
    return this.err;
  }

  public CalculatorException setErr(Error err) {
    this.err = err;
    return this;
  }

  public void unsetErr() {
    this.err = null;
  }

  /** Returns true if field err is set (has been assigned a value) and false otherwise */
  public boolean isSetErr() {
    return this.err != null;
  }

  public void setErrIsSet(boolean value) {
    if (!value) {
      this.err = null;
    }
  }

  public java.lang.String getWhat() {
    return this.what;
  }

  public CalculatorException setWhat(java.lang.String what) {
    this.what = what;
    return this;
  }

  public void unsetWhat() {
    this.what = null;
  }

  /** Returns true if field what is set (has been assigned a value) and false otherwise */
  public boolean isSetWhat() {
    return this.what != null;
  }

  public void setWhatIsSet(boolean value) {
    if (!value) {
      this.what = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case ERR:
      if (value == null) {
        unsetErr();
      } else {
        setErr((Error)value);
      }
      break;

    case WHAT:
      if (value == null) {
        unsetWhat();
      } else {
        setWhat((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case ERR:
      return getErr();

    case WHAT:
      return getWhat();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case ERR:
      return isSetErr();
    case WHAT:
      return isSetWhat();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof CalculatorException)
      return this.equals((CalculatorException)that);
    return false;
  }

  public boolean equals(CalculatorException that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_err = true && this.isSetErr();
    boolean that_present_err = true && that.isSetErr();
    if (this_present_err || that_present_err) {
      if (!(this_present_err && that_present_err))
        return false;
      if (!this.err.equals(that.err))
        return false;
    }

    boolean this_present_what = true && this.isSetWhat();
    boolean that_present_what = true && that.isSetWhat();
    if (this_present_what || that_present_what) {
      if (!(this_present_what && that_present_what))
        return false;
      if (!this.what.equals(that.what))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetErr()) ? 131071 : 524287);
    if (isSetErr())
      hashCode = hashCode * 8191 + err.getValue();

    hashCode = hashCode * 8191 + ((isSetWhat()) ? 131071 : 524287);
    if (isSetWhat())
      hashCode = hashCode * 8191 + what.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(CalculatorException other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetErr()).compareTo(other.isSetErr());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetErr()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.err, other.err);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetWhat()).compareTo(other.isSetWhat());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetWhat()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.what, other.what);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("CalculatorException(");
    boolean first = true;

    sb.append("err:");
    if (this.err == null) {
      sb.append("null");
    } else {
      sb.append(this.err);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("what:");
    if (this.what == null) {
      sb.append("null");
    } else {
      sb.append(this.what);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class CalculatorExceptionStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public CalculatorExceptionStandardScheme getScheme() {
      return new CalculatorExceptionStandardScheme();
    }
  }

  private static class CalculatorExceptionStandardScheme extends org.apache.thrift.scheme.StandardScheme<CalculatorException> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CalculatorException struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ERR
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.err = Service2.Error.findByValue(iprot.readI32());
              struct.setErrIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // WHAT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.what = iprot.readString();
              struct.setWhatIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, CalculatorException struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.err != null) {
        oprot.writeFieldBegin(ERR_FIELD_DESC);
        oprot.writeI32(struct.err.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.what != null) {
        oprot.writeFieldBegin(WHAT_FIELD_DESC);
        oprot.writeString(struct.what);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CalculatorExceptionTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public CalculatorExceptionTupleScheme getScheme() {
      return new CalculatorExceptionTupleScheme();
    }
  }

  private static class CalculatorExceptionTupleScheme extends org.apache.thrift.scheme.TupleScheme<CalculatorException> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CalculatorException struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetErr()) {
        optionals.set(0);
      }
      if (struct.isSetWhat()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetErr()) {
        oprot.writeI32(struct.err.getValue());
      }
      if (struct.isSetWhat()) {
        oprot.writeString(struct.what);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CalculatorException struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.err = Service2.Error.findByValue(iprot.readI32());
        struct.setErrIsSet(true);
      }
      if (incoming.get(1)) {
        struct.what = iprot.readString();
        struct.setWhatIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

