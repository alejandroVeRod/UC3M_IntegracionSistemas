package extractor.DistintivoMedioambiental;

public class DistintivoC extends Distintivo {
	
	String inicioPeriodoDistintivoGasolina = "";
	String inicioPeriodoDistintivoDiesel = "";
	
	public DistintivoC() {
		super();
	}

	public DistintivoC(String inicioPeriodoDistintivoGasolina, String inicioPeriodoDistintivoDiesel,
			String finPeriodoDistintivo, String finPeriodoDistintivoGasolina, String finPeriodoDistintivoDiesel) {
		super();
		this.inicioPeriodoDistintivoGasolina = inicioPeriodoDistintivoGasolina;
		this.inicioPeriodoDistintivoDiesel = inicioPeriodoDistintivoDiesel;
	}

	public String getInicioPeriodoDistintivoGasolina() {
		return inicioPeriodoDistintivoGasolina;
	}

	public void setInicioPeriodoDistintivoGasolina(String inicioPeriodoDistintivoGasolina) {
		this.inicioPeriodoDistintivoGasolina = inicioPeriodoDistintivoGasolina;
	}

	public String getInicioPeriodoDistintivoDiesel() {
		return inicioPeriodoDistintivoDiesel;
	}

	public void setInicioPeriodoDistintivoDiesel(String inicioPeriodoDistintivoDiesel) {
		this.inicioPeriodoDistintivoDiesel = inicioPeriodoDistintivoDiesel;
	}

}
