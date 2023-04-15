package lib;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

public class PersonalInformation {
    private final String firstName;
    private final String lastName;
    private final String idNumber;
    private final String address;
    private final boolean isForeigner;
    private final Gender gender;

    public PersonalInformation(String firstName, String lastName, String idNumber, String address, boolean isForeigner, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.address = address;
        this.isForeigner = isForeigner;
        this.gender = gender;
    }
}

public enum Gender {
    LAKI,
    PEREMPUAN
}

public class Employee {
    private final String employeeId;
    private final PersonalInformation personalInformation;
    private final int yearJoined;
    private final int monthJoined;
    private final int dayJoined;

    private int monthlySalary;
    private int otherMonthlyIncome;
    private int annualDeductible;

    private String spouseName;
    private String spouseIdNumber;

    private List<String> childNames;
    private List<String> childIdNumbers;
	
	public Employee(String employeeId, PersonalInformation personalInformation, int yearJoined, int monthJoined, int dayJoined) {
        this.employeeId = employeeId;
        this.personalInformation = personalInformation;
        this.yearJoined = yearJoined;
        this.monthJoined = monthJoined;
        this.dayJoined = dayJoined;

        childNames = new LinkedList<String>();
        childIdNumbers = new LinkedList<String>();
    }
	
	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3: 7.000.000 per bulan)
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 */
	
	public void setMonthlySalary(int grade) {
		if (grade == 1) {
			monthlySalary = hitungMonthlySalary(3000000);
		} else if (grade == 2) {
			monthlySalary = hitungMonthlySalary(5000000);
		} else if (grade == 3) {
			monthlySalary = hitungMonthlySalary(7000000);
		}
	}

	private int hitungMonthlySalary(int baseSalary) {
		int hasil = salaryAwal;
		if (isForeigner) {
			hasil = (int) (salaryAwal * 1.5);
		}
		return hasil;
	}

	
	public void setAnnualDeductible(int deductible) {	
		this.annualDeductible = deductible;
	}
	
	public void setAdditionalIncome(int income) {	
		this.otherMonthlyIncome = income;
	}
	
	public void setSpouse(String spouseName, String spouseIdNumber) {
		this.spouseName = spouseName;
		this.spouseIdNumber = idNumber;
	}
	
	public void addChild(String childName, String childIdNumber) {
		childNames.add(childName);
		childIdNumbers.add(childIdNumber);
	}
	
	public int getAnnualIncomeTax() {
		
		//Menghitung berapa lama pegawai bekerja dalam setahun ini, jika pegawai sudah bekerja dari tahun sebelumnya maka otomatis dianggap 12 bulan.
		LocalDate date = LocalDate.now();
		
		if (date.getYear() == yearJoined) {
			monthWorkingInYear = date.getMonthValue() - monthJoined;
		}else {
			monthWorkingInYear = 12;
		}
		
		return TaxFunction.calculateTax(monthlySalary, otherMonthlyIncome, monthWorkingInYear, annualDeductible, spouseIdNumber.equals(""), childIdNumbers.size());
	}
}
