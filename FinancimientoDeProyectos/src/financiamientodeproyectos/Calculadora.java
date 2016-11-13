/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financiamientodeproyectos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import sun.rmi.runtime.Log;

/**
 *
 * @author Freddy
 */
public class Calculadora {
    
    public static String bitacora = "";
    
    public static  BigDecimal metodoPrimario(BigDecimal monto, BigDecimal interes,Integer periodo){
		BigDecimal respuesta = null;
		BigDecimal interesCalc = interes.add(new BigDecimal(1));
		BigDecimal interesPeriod = interesCalc.pow(periodo);
		respuesta = monto.multiply(interesPeriod);
		
		return round(respuesta, 2, true);
	}
	public static List<Registro> metodoSecundario(BigDecimal monto, BigDecimal interes,Integer periodo){
		List<Registro> registros = new ArrayList<Registro>();
		Registro obj = null;
		for (int i = 0; i <= periodo; i++) {
			obj = new Registro();

			if(i == 0){//inicio
				obj.setAnio(new BigDecimal(i));
				obj.setInteres(round(new BigDecimal(0),2,true));
				obj.setPagoFinAnio(round(new BigDecimal(0),2,true));
				obj.setDeudaDespuesPago(monto);
			}else{
				obj.setAnio(new BigDecimal(i));
				obj.setInteres(round(monto.multiply(interes),2,true));
				if(i != periodo){
					obj.setPagoFinAnio(round(monto.multiply(interes),2,true));
					obj.setDeudaDespuesPago(monto);
	
				}else{//ultimo anio
					BigDecimal interesDelAnio = monto.multiply(interes);
					BigDecimal interesMasMonto = interesDelAnio.add(monto);
					obj.setPagoFinAnio(round(interesMasMonto,2,true));
					obj.setDeudaDespuesPago(round(new BigDecimal(0),2,true));
				}
			}
			registros.add(obj);
		}

		return registros;
	}
	
	public static List<Registro> metodoTerciario(BigDecimal monto, BigDecimal interes,Integer periodo){
		List<Registro> registros = new ArrayList<Registro>();
		Registro obj = null;
		BigDecimal montoTemp = null;
		BigDecimal pagoFinATemp = null;
		BigDecimal pagoPrincipalTemp = null;
		BigDecimal deudaFinal = null;
		for (int i = 0; i <= periodo; i++) {
			obj = new Registro();
			obj.setAnio(new BigDecimal(i));
			if(i == 0){ // primer registro
				montoTemp = monto;
				obj.setInteres(round(new BigDecimal(0),2,true));
				obj.setPagoFinAnio(round(new BigDecimal(0),2,true));
				obj.setPagoAPrincipal(round(new BigDecimal(0),2,true));
				obj.setDeudaDespuesPago(round(monto,2,true));
			}else{
				BigDecimal interesTemp = montoTemp.multiply(interes);
				obj.setInteres(round(interesTemp,2,true));
				pagoFinATemp = calculaMontoFinDeAnio(monto,interes,periodo);
				obj.setPagoFinAnio(pagoFinATemp);
				pagoPrincipalTemp = pagoFinATemp.subtract(interesTemp);
				obj.setPagoAPrincipal(round(pagoPrincipalTemp,2,true));
				deudaFinal = montoTemp.subtract(pagoPrincipalTemp);
				obj.setDeudaDespuesPago(round(deudaFinal,2,true));
				montoTemp = round(deudaFinal,2,true);
			}
			registros.add(obj);

		}

		return registros;
	}
	
	public static List<Registro> metodoCuarto(BigDecimal monto, BigDecimal interes,Integer periodo){
		List<Registro> registros = new ArrayList<Registro>();
		Registro obj = null;
		BigDecimal montoTemp = null;
		BigDecimal pagoFinATemp = null;
		BigDecimal pagoCapitalTemp = null;
		BigDecimal deudaFinal = null;
		for (int i = 0; i <= periodo; i++) {
			obj = new Registro();
			obj.setAnio(new BigDecimal(i));
			if(i == 0){ // primer registro
				montoTemp = monto;
				obj.setInteres(round(new BigDecimal(0),2,true));
				obj.setPagoFinAnio(round(new BigDecimal(0),2,true));
				obj.setPagoAPrincipal(round(new BigDecimal(0),2,true));
				obj.setDeudaDespuesPago(round(monto,2,true));
			}else{
				BigDecimal interesTemp = montoTemp.multiply(interes);
				obj.setInteres(round(interesTemp,2,true));
				pagoCapitalTemp =round(monto.multiply(new BigDecimal(0.2)),2,false); //20%
			    obj.setPagoACapital(pagoCapitalTemp); //20%
				pagoFinATemp = round(interesTemp.add(pagoCapitalTemp),2,true);
				obj.setPagoFinAnio(pagoFinATemp);
				deudaFinal = montoTemp.subtract(pagoCapitalTemp);
				obj.setDeudaDespuesPago(round(deudaFinal,2,true));
				montoTemp = round(deudaFinal,2,false);
			}
			registros.add(obj);

		}

		return registros;
	}
	
	public static BigDecimal round(BigDecimal d, int scale, boolean roundUp) {
		  int mode = (roundUp) ? BigDecimal.ROUND_UP : BigDecimal.ROUND_DOWN;
		  return d.setScale(scale, mode);
	}
	private static BigDecimal calculaMontoFinDeAnio(BigDecimal monto, BigDecimal interes,Integer periodo){
		BigDecimal op1 =  interes.add(new BigDecimal(1)); // (1+i)
		BigDecimal op2 = op1.pow(periodo); //(1+i) ^ n
		BigDecimal op3 = op2.multiply(interes); //i(1+i) ^ n
		BigDecimal op4 = op2.subtract(new BigDecimal(1)); //(1+i) ^ n -1
		BigDecimal op5 = op3.divide(op4, RoundingMode.HALF_UP);//i(1+i) ^ n / (1+i) ^ n -1
		BigDecimal op6 = op5.multiply(monto);//i(1+i) ^ n / (1+i) ^ n -1 [p]

		return  round(op6, 2, true);
	}
        
     public static String TIR1(BigDecimal monto,BigDecimal A,Integer periodo,BigDecimal ValorSalvamento){ //TIR con flujos constantes sin inflacion
       BigDecimal temp = new BigDecimal(0);
       BigDecimal TIR = new BigDecimal(0);
       int i =0;
       bitacora = "Cálculo de la TIR con flijos constantes sin inflación \n";
       bitacora = bitacora + "----------------------------------------------------\n";
       do{
           i++;
           TIR = TIR.add(new BigDecimal(0.001));
           BigDecimal op1 =  TIR.add(new BigDecimal(1)); // (1+i)
           BigDecimal op2 = op1.pow(periodo); //(1+i) ^ n
           BigDecimal op3 = op2.multiply(TIR); //i(1+i) ^ n
	   BigDecimal op4 = new BigDecimal(1).divide(op3, RoundingMode.HALF_DOWN);// 1 / i(1+i) ^ n
           BigDecimal op5 = op2.subtract(op4); //(1+i) ^ n -1 / i(1+i) ^ n
           BigDecimal op6 = op5.multiply(A);//i(1+i) ^ n / (1 +i) ^ n -1 [A]

           BigDecimal op7 = ValorSalvamento.divide(op2,RoundingMode.HALF_DOWN); // VS / (1+i) ^ n
           BigDecimal op8 = op6.add(op7);
           temp = op8;
           System.out.println("1TIR = "+TIR);
           bitacora = bitacora + "Iteracion["+i+"] - Monto Calculado = $"+temp+" ; TIR = %"+TIR+"\n";
       }while(monto.compareTo(temp) == 1); // monto > temp
         TIR = TIR.multiply(new BigDecimal(100));
         TIR = round(TIR, 2, true);
        
         return TIR.toString();
     }
     
     public static String TIR2(BigDecimal monto,BigDecimal A,Integer periodo,BigDecimal ValorSalvamento,BigDecimal inflacion){ //TIR con flujos constantes sin inflacion
       BigDecimal temp = new BigDecimal(0);
       BigDecimal VPN;
       BigDecimal TIR2 = new BigDecimal(1);
                  System.out.println("2TIR inicial = "+TIR2);
       bitacora = "Cálculo de la TIR con producción constante y considerando inflación \n";
       bitacora = bitacora + "----------------------------------------------------\n";
      // BigDecimal inflacion = new BigDecimal(1.2); // 20% de inflacion
       int x = 0;
       do{
          x++;
        TIR2 = TIR2.subtract(new BigDecimal(0.001));
         System.out.println("2TIR minu 0.01 = "+TIR2);
           temp  = A;
           BigDecimal op1 = new BigDecimal(0);
           VPN = new BigDecimal(0);
           for (int i = 1; i <= periodo; i++) {
            op1 =  temp.multiply(inflacion); 
            temp = op1;
            BigDecimal finalop = new BigDecimal(0);
             if(i != periodo){ 
                 BigDecimal op2 =  TIR2.add(new BigDecimal(1)); // (1+i)
                 BigDecimal op3 = op2.pow(i); //(1+i) ^ n
                 finalop = temp.divide(op3,RoundingMode.HALF_UP);
            }else{//ultimo periodo
                 BigDecimal op2 =  TIR2.add(new BigDecimal(1)); // (1+i)
                 BigDecimal op3 = op2.pow(i); //(1+i) ^ n
                 BigDecimal op4 = ValorSalvamento.multiply(inflacion);
                 BigDecimal op5 = temp.add(op4);
                 finalop = op5.divide(op3,RoundingMode.HALF_UP);
                 
            }
             System.out.println("Final op ["+i+"] = "+finalop);
            VPN = VPN.add(finalop);
           }
           System.out.println("VPN = "+VPN);
           System.out.println("2TIR = "+TIR2);
          
           bitacora = bitacora + "Iteracion["+x+"] - Monto Calculado = $"+VPN+" ; TIR = %"+TIR2+"\n";

       }while(monto.compareTo(VPN) == 1); // monto > temp
         TIR2 = TIR2.multiply(new BigDecimal(100));
         TIR2 = round(TIR2, 2, true);
        
         return TIR2.toString();
     }
     
       public static String TIR3(BigDecimal monto,BigDecimal A,Integer periodo,BigDecimal ValorSalvamento,BigDecimal inflacion, BigDecimal financiamiento
               ){ //TIR con flujos constantes sin inflacion
       bitacora = "Cálculo de la TIR con financiamiento \n";
       bitacora = bitacora + "----------------------------------------------------\n";
       BigDecimal temp = new BigDecimal(0);
       BigDecimal VPN;
       BigDecimal TIR3 = new BigDecimal(1);
                  System.out.println("3TIR inicial = "+TIR3);
       BigDecimal montoFinanciado = monto.subtract(financiamiento);
       //BigDecimal inflacion = new BigDecimal(1.2); // 20% de inflacion
       int x =0;
       do{
           x++;
        TIR3 = TIR3.subtract(new BigDecimal(0.001));
         System.out.println("3TIR minu 0.01 = "+TIR3);
           temp  = A;
           BigDecimal op1 = new BigDecimal(0);
           VPN = new BigDecimal(0);
           for (int i = 1; i <= periodo; i++) {
            op1 =  temp.multiply(inflacion); 
            temp = op1;
            BigDecimal finalop = new BigDecimal(0);
             if(i != periodo){ 
                 BigDecimal op2 =  TIR3.add(new BigDecimal(1)); // (1+i)
                 BigDecimal op3 = op2.pow(i); //(1+i) ^ n
                 finalop = temp.divide(op3,RoundingMode.HALF_UP);
            }else{//ultimo periodo
                 BigDecimal op2 =  TIR3.add(new BigDecimal(1)); // (1+i)
                 BigDecimal op3 = op2.pow(i); //(1+i) ^ n
                 BigDecimal op4 = ValorSalvamento.multiply(inflacion);
                 BigDecimal op5 = temp.add(op4);
                 finalop = op5.divide(op3,RoundingMode.HALF_UP);
                 
            }
             System.out.println("Final op ["+i+"] = "+finalop);
            VPN = VPN.add(finalop);
           }
           System.out.println("VPN = "+VPN);
           System.out.println("3TIR = "+TIR3);
           bitacora = bitacora + "Iteracion["+x+"] - Monto Calculado = $"+VPN+" ; TIR = %"+TIR3+"\n";


       }while(montoFinanciado.compareTo(VPN) == 1); // monto > temp
         TIR3 = TIR3.multiply(new BigDecimal(100));
         TIR3 = round(TIR3, 2, true);
        
         return TIR3.toString();
     }
        
     public String formatNumber(BigDecimal number) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        return formatter.format(number);
    }
    
}
