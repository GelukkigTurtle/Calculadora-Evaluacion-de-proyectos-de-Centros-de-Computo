/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financiamientodeproyectos;

import java.math.BigDecimal;

/**
 *
 * @author Freddy
 */
public class Registro {
    BigDecimal anio;
	BigDecimal interes;
	BigDecimal pagoFinAnio;
	BigDecimal deudaDespuesPago;
	BigDecimal pagoACapital;
	BigDecimal pagoAnual;
	BigDecimal pagoAPrincipal;
	
	
	public Registro() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BigDecimal getAnio() {
		return anio;
	}
	public void setAnio(BigDecimal anio) {
		this.anio = anio;
	}
	public BigDecimal getInteres() {
		return interes;
	}
	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}
	public BigDecimal getPagoFinAnio() {
		return pagoFinAnio;
	}
	public void setPagoFinAnio(BigDecimal pagoFinAnio) {
		this.pagoFinAnio = pagoFinAnio;
	}
	public BigDecimal getDeudaDespuesPago() {
		return deudaDespuesPago;
	}
	public void setDeudaDespuesPago(BigDecimal deudaDespuesPago) {
		this.deudaDespuesPago = deudaDespuesPago;
	}
	public BigDecimal getPagoACapital() {
		return pagoACapital;
	}
	public void setPagoACapital(BigDecimal pagoACapital) {
		this.pagoACapital = pagoACapital;
	}
	public BigDecimal getPagoAnual() {
		return pagoAnual;
	}
	public void setPagoAnual(BigDecimal pagoAnual) {
		this.pagoAnual = pagoAnual;
	}
	public BigDecimal getPagoAPrincipal() {
		return pagoAPrincipal;
	}
	public void setPagoAPrincipal(BigDecimal pagoAPrincipal) {
		this.pagoAPrincipal = pagoAPrincipal;
	}
	
	
	
}
