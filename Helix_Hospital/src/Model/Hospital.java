package Model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Hospital {
    private Connection conn;

    public Hospital(Connection conn){
        this.conn = conn;
    }

    public boolean addPatient(String firstName, String lastName, String middleInit, String email, String address,
                              Patient.Gender gender, String insurance_ID, String phoneNo, String doctor) {
    	boolean committed = false;
    	String insertStatement = "insert into patient(first_name, middle_initial, " +
                "last_name, email, address, sex, insurance_id, phone, doctor) "
    			+ "VALUES(?,?,?,?,?,?,?,?,?)";
    	PreparedStatement preparedStmt;

    	try {
    		preparedStmt = conn.prepareStatement(insertStatement);
    		preparedStmt.setString (1, firstName);
    		preparedStmt.setString (2, middleInit);
    		preparedStmt.setString (3, lastName);
    		preparedStmt.setString (4, email);
    		preparedStmt.setString (5, address);
    		preparedStmt.setString (6, gender == null ? null : gender.name());
    		preparedStmt.setString (7, insurance_ID);
    		preparedStmt.setString (8, phoneNo);
    		preparedStmt.setString(9, doctor);

    		// execute the preparedstatement
    		preparedStmt.execute();
    		committed = true;
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	finally {
    		if(!committed) {
    			try {
    				conn.rollback();
    			} catch (SQLException e) {
    				e.printStackTrace();
    			}
    			return false;
    		}
    	} 
    	return true;
    }

    public Employee getEmployee(String username){
        String sql = "SELECT first_name, last_name, username, type FROM employee WHERE username = '" + username + "';";
        try {
            Statement st = conn.createStatement();
            ResultSet result = st.executeQuery(sql);
            if (!result.next()){
                return null;
            }
            Employee emp = new Employee();
            emp.setFirstName(result.getString("first_name"));
            emp.setLastName(result.getString("last_name"));
            emp.setUsername(result.getString("username"));
            emp.setType(Employee.Type.fromString(result.getString("type")));
            return emp;

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        //do something with the sql
        return null;
    }

    public Employee getEmployeeBySSN(String ssn){
        String sql = "SELECT first_name, last_name, username, type FROM employee WHERE ssn = '" + ssn + "';";
        try {
            Statement st = conn.createStatement();
            ResultSet result = st.executeQuery(sql);
            if (!result.next()){
                return null;
            }
            Employee emp = new Employee();
            emp.setFirstName(result.getString("first_name"));
            emp.setLastName(result.getString("last_name"));
            emp.setUsername(result.getString("username"));
            emp.setType(Employee.Type.fromString(result.getString("type")));
            return emp;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        //do something with the sql
        return null;
    }

    public boolean addDoctor(String username, String firstName, String middleInit, String lastName,
                             String phoneNo, Employee.Gender gender, String ssn, String email, String address,
                             Employee.Type type, String special){

        addEmployee(username, firstName, middleInit, lastName, phoneNo, gender, ssn, email, address, type);

        String insertStatement = "INSERT INTO doctor(username, specialization) VALUES (?,?);";

        try {
			PreparedStatement preparedStmt = conn.prepareStatement(insertStatement);
			preparedStmt.setString (1, username);
    		preparedStmt.setString (2, special);
    		preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
        
        return true;
    }
    
    public boolean addEmployee(String username, String firstName, String middleInit, String lastName,
            String phoneNo, Employee.Gender gender, String ssn, String email, String address, Employee.Type type) {
    	boolean committed = false;
    	String insertStatement = "insert into employee(username, first_name, middle_initial, last_name, phone, sex, ssn, email, address, type) "
    			+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
    	PreparedStatement preparedStmt;
    	try {
    		preparedStmt = conn.prepareStatement(insertStatement);
    		preparedStmt.setString (1, username);
    		preparedStmt.setString (2, firstName);
    		preparedStmt.setString (3, middleInit);
    		preparedStmt.setString (4, lastName);
    		preparedStmt.setString (5, phoneNo);
    		preparedStmt.setString (6, gender == null ? null : gender.name());
    		preparedStmt.setString (7, ssn);
    		preparedStmt.setString (8, email);
    		preparedStmt.setString (9, address);
    		preparedStmt.setString (10, type.name());
    		preparedStmt.execute();
    		committed = true;
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	finally {
    		if(!committed) {
    			try {
    				conn.rollback();
    			} catch (SQLException e) {
    				e.printStackTrace();
    			}
    			return false;
    		}
    	} 

    	return true;
    }
    
    public boolean addAppointment(String username, String patientID, LocalDateTime dateTime, String reasonForVisit) {
    	boolean committed = false;
    	String sql = "insert into appointment(username, patient_id, date, reason_for_visit) "
    			+ " VALUES(?,?,?,?)";
    	PreparedStatement preparedStmt;
    	try {
    		preparedStmt = conn.prepareStatement(sql);
    		preparedStmt.setString(1, username);
    		preparedStmt.setString(2, patientID);
    		Timestamp ts = Timestamp.valueOf(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    		preparedStmt.setTimestamp(3, ts);
    		preparedStmt.setString(4, reasonForVisit);
    		preparedStmt.execute();
    		committed = true;
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	finally {
    		if(!committed) {
    			try {
    				conn.rollback();
    			} catch (SQLException e) {
    				e.printStackTrace();
    			}
    			return false;
    		}
    	} 
    	return true;
    }
    
    public boolean addPrescription(String patientID, String drugName, String dosage, String duration) {
    	String sql = "insert into prescription(patient_ID, drug_name, dosage, duration) "
    			+ " VALUES(?,?,?,?)";
    	boolean committed = false;
    	PreparedStatement preparedStmt;
    	try {
    		preparedStmt = conn.prepareStatement(sql);
    		preparedStmt.setString(1, patientID);
    		preparedStmt.setString(2, drugName);
    		preparedStmt.setString(3, dosage);
    		preparedStmt.setString(4, duration);
    		preparedStmt.execute();
    		committed = true;
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	finally {
    		if(!committed) {
    			try {
    				conn.rollback();
    			} catch (SQLException e) {
    				e.printStackTrace();
    			}
    			return false;
    		}
    	} 
    	return true;
    }
    
    public boolean addMedicalHistory (String patientID, String bloodType, String allergies, 
    		String medications, String pastConditions, String familyHistory) {
    	boolean committed = false;
    	String sql = "insert into medical_history(patient_ID, blood_type, family_history, past_Conditions, allergies, medications) "
    			+ " VALUES(?,?,?,?,?,?)";
    	PreparedStatement preparedStmt;
    	try {
    		preparedStmt = conn.prepareStatement(sql);
    		preparedStmt.setString(1, patientID);
    		preparedStmt.setString(2, bloodType);
    		preparedStmt.setString(3, familyHistory);
    		preparedStmt.setString(4, pastConditions);
    		preparedStmt.setString(5, allergies);
    		preparedStmt.setString(6, medications);
    		preparedStmt.execute();
    		committed = true;
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	finally {
    		if(!committed) {
    			try {
    				conn.rollback();
    			} catch (SQLException e) {
    				e.printStackTrace();
    			}
    			return false;
    		}
    	} 
    	return true;
    }

    public boolean addMedicalRecord (String username, String patientID, LocalDateTime dateTime, String notes) {
    	boolean committed = false;
    	String sql = "insert into medical_record(username, patient_id, date, notes) "
    			+ " VALUES(?,?,?,?)";
    	PreparedStatement preparedStmt;
    	try {
    		preparedStmt = conn.prepareStatement(sql);
    		preparedStmt.setString(1, username);
    		preparedStmt.setString(2, patientID);
            Timestamp ts = Timestamp.valueOf(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    		preparedStmt.setTimestamp(3, ts);
    		preparedStmt.setString(4, notes);
    		preparedStmt.execute();
    		committed = true;
    	} catch (SQLException e) {
    		e.printStackTrace();

    	}
    	finally {
    		if(!committed) {
    			try {
    				conn.rollback();
    			} catch (SQLException e) {
    				e.printStackTrace();
    			}
    			return false;
    		}
    	} 
    	return true;
    }

    public Iterator<Employee> getAllDoctors(){
        String sql = "SELECT first_name, last_name, Doctor.username, type " +
                     "FROM Doctor, Employee " +
                     "WHERE Doctor.username = Employee.username";
        ArrayList<Employee> doctors = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet set = st.executeQuery(sql);

            while(set.next()){
                Employee doctor = new Employee();
                doctor.setFirstName( set.getString("first_name") );
                doctor.setLastName( set.getString("last_name") );
                doctor.setUsername( set.getString("username") );
                doctor.setType(Employee.Type.fromString(set.getString("type")));
                doctors.add(doctor);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return doctors.iterator();
    }

    public List<Employee> getAllEmployees(){
        String sql = "SELECT first_name, middle_initial, last_name, username, phone, " +
                "sex, ssn, email, address, type FROM Employee";
        List<Employee> employees = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet set = st.executeQuery(sql);

            while(set.next()){
                Employee employee = new Employee();
                employee.setFirstName( set.getString("first_name") );
                employee.setMiddleInit(set.getString("middle_initial"));
                employee.setLastName( set.getString("last_name") );
                employee.setUsername( set.getString("username") );
                employee.setPhoneNumber(set.getString("phone"));
                switch(set.getString("sex")){
                    case "M":
                        employee.setGender(Person.Gender.M);
                        break;
                    case "F":
                        employee.setGender(Person.Gender.F);
                        break;
                    case "O":
                        employee.setGender(Person.Gender.O);
                        break;
                }
                employee.setSsn(set.getString("ssn"));
                employee.setEmail(set.getString("email"));
                employee.setAddress(set.getString("address"));
                employee.setType(Employee.Type.fromString(set.getString("type")));
                employees.add(employee);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return employees;
    }

    public boolean doctorExists(String username){
        String sql = "SELECT username FROM Doctor WHERE username = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setString (1, username);
            ResultSet set = preparedStmt.executeQuery();
            return (set.next()) ? true : false;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean doctorExists(){
        String sql = "SELECT username FROM Doctor";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            ResultSet set = preparedStmt.executeQuery();
            return (set.next()) ? true : false;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Patient> getAllPatients(){
        String sql = "SELECT patient_ID, first_name, middle_initial, last_name, email, " +
                "address, sex, insurance_ID, phone, doctor FROM PATIENT";
        try {
            Statement st = conn.createStatement();
            ResultSet set = st.executeQuery(sql);

            List<Patient> patients = new ArrayList<>();

            while(set.next()){
                Patient patient = new Patient();
                patient.setPatientID( set.getString("patient_ID") );
                patient.setFirstName( set.getString("first_name") );
                patient.setMiddleInit(set.getString("middle_initial"));
                patient.setLastName( set.getString("last_name") );
                patient.setEmail(set.getString("email"));
                patient.setAddress(set.getString("address"));
                switch(set.getString("sex")){
                    case "M":
                        patient.setGender(Person.Gender.M);
                        break;
                    case "F":
                        patient.setGender(Person.Gender.F);
                        break;
                    case "O":
                        patient.setGender(Person.Gender.O);
                        break;
                }
                patient.setInsuranceID( set.getString("insurance_Id") );
                patient.setPhoneNumber(set.getString("phone"));
                patient.setDoctor( set.getString("doctor") );
                patients.add(patient);
            }
            return patients;

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Patient> getPatientsOfDoctor(Employee doctor){
        String sql = "SELECT patient_ID, first_name, middle_initial, last_name, email, "
                + "address, sex, insurance_ID, phone, doctor "
                + "FROM Patient "
                + "WHERE doctor = '" + doctor.getUsername() + "'";
        try {
            Statement st = conn.createStatement();
            ResultSet set = st.executeQuery(sql);

            List<Patient> patients = new ArrayList<>();

            while(set.next()){
                Patient patient = new Patient();
                patient.setPatientID( set.getString("patient_ID") );
                patient.setFirstName( set.getString("first_name") );
                patient.setMiddleInit(set.getString("middle_initial"));
                patient.setLastName( set.getString("last_name") );
                patient.setEmail(set.getString("email"));
                patient.setAddress(set.getString("address"));
                switch(set.getString("sex")){
                    case "M":
                        patient.setGender(Person.Gender.M);
                        break;
                    case "F":
                        patient.setGender(Person.Gender.F);
                        break;
                    case "O":
                        patient.setGender(Person.Gender.O);
                        break;
                }
                patient.setInsuranceID( set.getString("insurance_Id") );
                patient.setPhoneNumber(set.getString("phone"));
                patient.setDoctor( set.getString("doctor") );
                patients.add(patient);
            }
            return patients;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        //do something with the sql
        return null;
    }

    public MedicalHistory getMedicalHistory(Patient patient) {
        String sql = "SELECT patient_ID, blood_type, family_history, past_conditions, allergies, medications "
                + "FROM medical_history "
                + "WHERE patient_ID = '" + patient.getPatientID() + "'";
        try {
            Statement st = conn.createStatement();
            ResultSet result = st.executeQuery(sql);
            if (!result.next()) {
                return null;
            }
            MedicalHistory medicalHistory = new MedicalHistory();

            medicalHistory.setPatientID(result.getString("patient_ID"));
            medicalHistory.setBloodType(result.getString("blood_type"));
            medicalHistory.setFamilyHistory(result.getString("family_history"));
            medicalHistory.setPastConditions(result.getString("past_conditions"));
            medicalHistory.setAllergies(result.getString("allergies"));
            medicalHistory.setMedications(result.getString("medications"));
            return medicalHistory;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        //medical history not found
        return null;
    }

    public List<Appointment> getAppointmentsOfPatient(String patientId){
        String sql = "SELECT username, patient_ID, date, reason_for_visit "
                + "FROM appointment "
                + "WHERE patient_ID = '" + patientId + "'";
        try {
            Statement st = conn.createStatement();
            ResultSet set = st.executeQuery(sql);

            List<Appointment> appointments = new ArrayList<>();

            while(set.next()){
                Appointment appointment = new Appointment();
                appointment.setUsername( set.getString("username") );
                appointment.setPatientID( set.getString("patient_ID") );
                appointment.setDateTime( set.getString("date") );
                appointment.setReasonForVisit( set.getString("reason_for_visit") );

                appointments.add(appointment);
            }
            return appointments;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        //appointments not found
        return null;
    }

    public List<Appointment> getAppointmentsOfEmployee(String username){
        String sql = "SELECT username, patient_ID, date, reason_for_visit "
                + "FROM appointment "
                + "WHERE patient_ID = '" + username + "'";
        try {
            Statement st = conn.createStatement();
            ResultSet set = st.executeQuery(sql);

            List<Appointment> appointments = new ArrayList<>();

            while(set.next()){
                Appointment appointment = new Appointment();
                appointment.setUsername( set.getString("username") );
                appointment.setPatientID( set.getString("patient_ID") );
                appointment.setDateTime( set.getString("date") );
                appointment.setReasonForVisit( set.getString("reason_for_visit") );

                appointments.add(appointment);
            }
            return appointments;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        //appointments not found
        return null;
    }

    public List<MedicalRecord> getRecordsOfPatient(String patientId){
        String sql = "SELECT username, patient_ID, date, notes "
                + "FROM medical_record "
                + "WHERE patient_ID = '" + patientId + "'";
        try {
            Statement st = conn.createStatement();
            ResultSet set = st.executeQuery(sql);

            List<MedicalRecord> medicalRecords = new ArrayList<>();

            while(set.next()){
                System.out.println("adding a new record");
                MedicalRecord medicalRecord = new MedicalRecord();
                medicalRecord.setUsername( set.getString("username") );
                medicalRecord.setPatientID( set.getString("patient_ID") );
                medicalRecord.setDateTime( set.getString("date") );
                medicalRecord.setNotes( set.getString("notes") );

                medicalRecords.add(medicalRecord);
            }
            return medicalRecords;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        //records not found
        return null;
    }

    public List<Prescription> getPrescriptionsOfPatient(String patientId) {
        String sql = "SELECT patient_ID, prescription_ID, drug_name, dosage, duration "
                + "FROM prescription "
                + "WHERE patient_ID = '" + patientId + "'";
        try {
            Statement st = conn.createStatement();
            ResultSet set = st.executeQuery(sql);

            List<Prescription> prescriptions = new ArrayList<>();

            while(set.next()){
                Prescription prescription = new Prescription();
                prescription.setPatientID( set.getString("patient_ID") );
                prescription.setPrescriptionID( set.getString("prescription_ID") );
                prescription.setDrugName( set.getString("drug_name") );
                prescription.setDosage( set.getString("dosage") );
                prescription.setDuration( set.getString("duration") );
                prescriptions.add(prescription);
            }
            return prescriptions;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        //prescriptions not found
        return null;
    }

    private String formatString(String s){
        if(s == null) return null;
        else return "'" + s + "'";

    }
    
    public boolean updatePatient(Patient patient) {
    	boolean committed = false;
    	String sql = 
    			   "UPDATE patient " + 
    			   "  SET first_name = ?, middle_initial = ?,last_name = ?, email = ?, address = ?, sex = ?, insurance_id = ?, phone = ?, doctor = ?" +
    			   "WHERE patient_ID = ?";
    			PreparedStatement pstmt;
				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, patient.getFirstName());
					pstmt.setString(2, patient.getMiddleInit());
					pstmt.setString(3, patient.getLastName());
					pstmt.setString(4, patient.getEmail());
					pstmt.setString(5, patient.getAddress());
					pstmt.setString(6, patient.getGender() == null ? null : patient.getGender().name());
					pstmt.setString(7, patient.getInsuranceID());
					pstmt.setString(8, patient.getPhoneNumber());
					pstmt.setString(9, patient.getDoctor());
	    			pstmt.setString(10, patient.getPatientID());
	    			pstmt.executeUpdate();
	    			committed = true;	
				}
				catch (SQLException e){
					e.printStackTrace();
					return false;
				} 
	    		finally {
					if(!committed) {
						try {
							conn.rollback();
						} catch (SQLException e) {
							e.printStackTrace();
							return false;
						}
					}
				} 
    	return true;
    }
    
    public boolean updateEmployee(Employee employee) {
    	boolean committed = false;
    	String sql = 
    			   "UPDATE employee " + 
    			   "  SET first_name = ?, middle_initial = ?, last_name = ?, phone = ?, sex = ?, ssn = ?, email = ?, address = ?, type = ?" +
    			   "WHERE username = ?";
    			PreparedStatement pstmt;
				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, employee.getFirstName());
					pstmt.setString(2, "" + employee.getMiddleInit());
					pstmt.setString(3, employee.getLastName());
					pstmt.setString(4, employee.getPhoneNumber());
					pstmt.setString(5, employee.getGender() == null ? null : employee.getGender().name());
					pstmt.setString(6, employee.getSsn());
					pstmt.setString(7, employee.getEmail());
					pstmt.setString(8, employee.getAddress());
	    			pstmt.setString(9, employee.getType().name());
	    			pstmt.setString(10, employee.getUsername());
	    			pstmt.executeUpdate();
	    			committed = true;	
				}
				catch (SQLException e){
					e.printStackTrace();
					return false;
				} 
	    		finally {
					if(!committed) {
						try {
							conn.rollback();
						} catch (SQLException e) {
							e.printStackTrace();
							return false;
						}
					}
				} 
    	return true;
    }
    
    public boolean updateAppointment(Appointment appointment, LocalDateTime date) {
    	boolean committed = false;
    	String sql = 
    			   "UPDATE appointment " + 
    			   "  SET date = ?, reason_for_visit = ?" +
    			   "WHERE username = ?, patient_ID = ?, date = ?";
    			PreparedStatement pstmt;
				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, date.toString());
					pstmt.setString(2, appointment.getReasonForVisit());
					pstmt.setString(3, appointment.getUsername());
					pstmt.setString(4, appointment.getPatientID());
					pstmt.setString(5, appointment.getDateTime().toString());
	    			pstmt.executeUpdate();
	    			committed = true;	
				}
				catch (SQLException e){
					e.printStackTrace();
					return false;
				} 
	    		finally {
					if(!committed) {
						try {
							conn.rollback();
						} catch (SQLException e) {
							e.printStackTrace();
							return false;
						}
					}
				} 
    	return true;
    }
    
    public boolean updatePrescription(Prescription prescription) {
    	boolean committed = false;
    	String sql = 
    			   "UPDATE prescripton " + 
    			   "  SET prescription_id = ?, drug_name = ?, dosage = ?, duration = ?" +
    			   "WHERE patient_ID = ?";
    			PreparedStatement pstmt;
				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, prescription.getPrescriptionID());
					pstmt.setString(2, prescription.getDrugName());
					pstmt.setString(3, prescription.getDosage());
					pstmt.setString(4, prescription.getDuration());
					pstmt.setString(5, prescription.getPatientID());
	    			pstmt.executeUpdate();
	    			committed = true;	
				}
				catch (SQLException e){
					e.printStackTrace();
					return false;
				} 
	    		finally {
					if(!committed) {
						try {
							conn.rollback();
						} catch (SQLException e) {
							e.printStackTrace();
							return false;
						}
					}
				} 
    	return true;
    }
    
    public boolean updateMedicalHistory(MedicalHistory mh) {
    	boolean committed = false;
    	String sql = 
    			   "UPDATE medical_history " + 
    			   "  SET blood_type = ?, family_history = ?, past_conditions = ?, allergies = ?, medications = ?" +
    			   "WHERE patient_ID = ?";
    			PreparedStatement pstmt;
				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, mh.getBloodType());
					pstmt.setString(2, mh.getFamilyHistory());
					pstmt.setString(3, mh.getPastConditions());
					pstmt.setString(4, mh.getAllergies());
					pstmt.setString(5, mh.getMedications());
					pstmt.setString(6, mh.getPatientID());
	    			pstmt.executeUpdate();
	    			committed = true;	
				}
				catch (SQLException e){
					e.printStackTrace();
					return false;
				} 
	    		finally {
					if(!committed) {
						try {
							conn.rollback();
						} catch (SQLException e) {
							e.printStackTrace();
							return false;
						}
					}
				} 
    	return true;
    }
    

    public static String formatGender(Employee.Gender g){
        if(g == null) return null;
        switch (g){
            case O:
                return "O";
            case F:
                return "F";
            case M:
                return "M";
            default:
                return "H";
        }

    }

    public static String formatType(Employee.Type t){
        if(t == null) return null;
        switch (t){
            case DOCTOR:
                return "Doctor";
            case NURSE:
                return "Nurse";
            case SUPPORT:
                return "Support";
            default:
                return "H";
        }
    }
    
    

}
