package com.meli.jfraile.bo;

import com.meli.jfraile.dto.MutantDTO;
import com.meli.jfraile.dto.StatsResponseDTO;
import com.meli.jfraile.infraestructure.database.mysql.model.MutantValidationEntity;
import com.meli.jfraile.infraestructure.database.mysql.persistence.MySQLRepository;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@NoArgsConstructor
public class MagnetoBO {

    private static final String TEXTO_A = "AAAA";
    private static final String TEXTO_C = "CCCC";
    private static final String TEXTO_G = "GGGG";
    private static final String TEXTO_T = "TTTT";

    @Value("${magneto.max_occurs}")
    private Integer max;

    @Inject
    MySQLRepository mySQLRepository;

    /**
     * Método que a partir de una matriz, valida si la secuencia de ADN ingresada corresponde a un mutante
     * @param lines matriz a validar
     * @return true si es mutante, false en caso contrario.
     */
    public boolean validateMutant (String[] lines){
        int count = 0;
        count = validateHorizontalLines(lines, 0);
        if(count >= max){
            saveValidation(lines, true);
            return true;
        }
        count = validateVerticalLines(lines, count);
        if(count >= max){
            saveValidation(lines, true);
            return true;
        }
        count = validateDiagonalLines(lines, count);
        if(count >= max){
            saveValidation(lines, true);
            return true;
        }
        count = validateReverseDiagonalLines(lines, count);
        if(count >= max){
            saveValidation(lines, true);
            return true;
        }
        saveValidation(lines, false);
        return false;
    }

    /**
     * Método que valida la ocurrencia de las constantes definidas en las líneas horizontales de la matriz.
     * Si el número máximo de ocurrencias es superado en algún momento, el método retorna para no continuar ejecutando validaciones
     * @param lines matriz a validar
     * @param counter número de ocurrencias encontradas hasta el momento por las validaciones previas
     * @return counter + número de ocurrencias encontradas en las horizontales
     */
    private int validateHorizontalLines (String[] lines, int counter){
        int innerCount = counter;
        for(String line : lines){
            //System.out.println("ValidandoH: " + line);
            int countInLine = validateLine(line);
            innerCount = innerCount + countInLine;
            if(innerCount >= max){
                //System.out.println("Mutante");
                return innerCount;
            }
        }
        return innerCount;
    }

    /**
     * Método que valida la ocurrencia de las constantes definidas en las líneas verticales de la matriz.
     * Si el número máximo de ocurrencias es superado en algún momento, el método retorna para no continuar ejecutando validaciones
     * @param lines matriz a validar
     * @param counter número de ocurrencias encontradas hasta el momento por las validaciones previas
     * @return counter + número de ocurrencias encontradas en las verticales
     */
    private int validateVerticalLines (String[] lines, int counter){
        int innerCount = counter;
        for(int x = 0 ; x < lines.length ; x++){
            StringBuilder sb = new StringBuilder();
            for(int i = 0 ; i < lines.length ; i++){
                sb.append(lines[i].charAt(x));
            }
            //System.out.println("ValidandoV: " + sb.toString());
            int countInLine = validateLine(sb.toString());
            innerCount = innerCount + countInLine;
            if(innerCount >= max){
                //System.out.println("Mutante");
                return innerCount;
            }
        }
        return innerCount;
    }

    /**
     * Método que obtiene una diagonal der-izq a partir de la posición de la esquina ingresada
     * @param fileCorner posición de fila de la letra superior de la diagonal
     * @param colCorner posición de columna de la letra superior de la diagonal
     * @param lines matriz de donde se obtiene la diagonal
     * @return String de diagonal resultante
     */
    private String obtainDiagonalLine (int fileCorner, int colCorner, String[] lines){
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; fileCorner + i < lines.length && colCorner + i < lines.length ; i++){
            sb.append(lines[fileCorner + i].charAt(colCorner + i));
        }
        return sb.toString();
    }

    /**
     * Método que obtiene una diagonal izq-der a partir de la posición de la esquina ingresada
     * @param fileCorner posición de fila de la letra superior de la diagonal
     * @param colCorner posición de columna de la letra superior de la diagonal
     * @param lines matriz de donde se obtiene la diagonal
     * @return String de diagonal resultante
     */
    private String obtainReverseDiagonalLine (int fileCorner, int colCorner, String[] lines){
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; fileCorner + i < lines.length && colCorner - i >= 0 ; i++){
            sb.append(lines[fileCorner + i].charAt(colCorner - i));
        }
        return sb.toString();
    }

    /**
     * Método que valida los String correspondientes a las diagonales en sentido der-izq de la matriz para encontrar
     *  cadenas validadas e identificar si se trata de un mutante.
     *  Si el número máximo de ocurrencias es superado en algún momento, el método retorna para no continuar ejecutando validaciones
     * @param lines matriz a validar
     * @param counter número de ocurrencias encontradas hasta el momento por las validaciones previas
     * @return counter + número de ocurrencias encontradas en las diagonales der-izq
     */
    private int validateDiagonalLines (String[] lines, int counter){
        int innerCount = counter;
        for(int i = 0 ; i <= lines.length - max ; i++){
            //System.out.println("ValidandoD1: " + obtainDiagonalLine(0, i, lines));
            int countInLine = validateLine(obtainDiagonalLine(0, i, lines));
            innerCount = innerCount + countInLine;
            if(innerCount >= max){
                //System.out.println("Mutante");
                return innerCount;
            }
        }
        for(int i = 1 ; i <= lines.length - max ; i++){
            //System.out.println("ValidandoD2: " + obtainDiagonalLine(i, 0, lines));
            int countInLine = validateLine(obtainDiagonalLine(i, 0, lines));
            innerCount = innerCount + countInLine;
            if(innerCount >= max){
                //System.out.println("Mutante");
                return innerCount;
            }
        }
        return innerCount;
    }

    /**
     * Método que valida los String correspondientes a las diagonales en sentido izq-der de la matriz para encontrar
     *  cadenas validadas e identificar si se trata de un mutante.
     *  Si el número máximo de ocurrencias es superado en algún momento, el método retorna para no continuar ejecutando validaciones
     * @param lines matriz a validar
     * @param counter número de ocurrencias encontradas hasta el momento por las validaciones previas
     * @return counter + número de ocurrencias encontradas en las diagonales izq-der
     */
    private int validateReverseDiagonalLines (String[] lines, int counter){
        int innerCount = counter;
        for(int i = lines.length - 1 ; i >= max - 1 ; i--){
            //System.out.println("ValidandoRD1: " + obtainReverseDiagonalLine(0, i, lines));
            int countInLine = validateLine(obtainReverseDiagonalLine(0, i, lines));
            innerCount = innerCount + countInLine;
            if(innerCount >= max){
                //System.out.println("Mutante");
                return innerCount;
            }
        }
        for(int i = 1 ; i <= lines.length - max ; i++){
            //System.out.println("ValidandoRD2: " + obtainReverseDiagonalLine(i, lines.length - 1, lines));
            int countInLine = validateLine(obtainReverseDiagonalLine(i, lines.length - 1, lines));
            innerCount = innerCount + countInLine;
            if(innerCount >= max){
                //System.out.println("Mutante");
                return innerCount;
            }
        }
        return innerCount;
    }

    /**
     * Método que valida la existencia de alguno de los textos validados (AAAA,CCCC,GGGG,TTTT) en un String
     * @param line String a validar
     * @return Cantidad de veces que se encuentran las constantes definidas en el String
     */
    private int validateLine (String line){
        return StringUtils.countMatches(line, "AAAA") +
                StringUtils.countMatches(line, "CCCC") +
                StringUtils.countMatches(line, "GGGG") +
                StringUtils.countMatches(line, "TTTT");
    }

    /**
     * Método que obtiene todo el contenido de una matriz en un solo string para utilizarlo como id en BD
     * @param lines matriz a convertir
     * @return String concatenando todos los Strings del arreglo
     */
    private String getMatrixString (String[] lines){
        StringBuilder sb = new StringBuilder();
        for(String line : lines){
            sb.append(line);
        }
        return sb.toString();
    }

    /**
     * Método que obtiene el SHA512 de un String para poder estandarizar la longitud del ADN validado en BD
     * @param line String a convertir
     * @return SHA512 del valor ingresado
     */
    private String getSha512 (String line){
        return Base64.encodeBase64String(DigestUtils.sha512(line));
    }

    /**
     * Método que almacena en BD un registro de validación para un ADN
     * @param lines
     * @param result
     */
    private void saveValidation (String[] lines, Boolean result){
        String line = getMatrixString(lines);
        String id = getSha512(line);
        MutantValidationEntity entity = mySQLRepository.getMutantValidationById(id);
        if(entity == null){
            entity = new MutantValidationEntity(id, result);
            mySQLRepository.saveToMutantValidation(entity);
        }
    }

    public StatsResponseDTO stats (){
        Integer mutants = mySQLRepository.getMutantValidationCountByResult(true);
        Integer humans = mySQLRepository.getMutantValidationCountByResult(false);
        Double ratio = Double.valueOf(mutants / humans);
        return new StatsResponseDTO(mutants, humans, ratio);
    }



}
