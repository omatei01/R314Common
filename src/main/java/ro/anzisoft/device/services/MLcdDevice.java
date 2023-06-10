package ro.anzisoft.device.services;

import jpos.JposException;
import jpos.services.BaseService;

/**
 * Customer Display<br>
 * Doar functii proprii<br>
 *
 * @author omatei01
 */
public interface MLcdDevice extends BaseService {

	/**
	 * Afișeaza pe display textele primite in parametrii
	 *
	 * @param row1 Textul care va fi afișat pe rîndul 1
	 * @param row2 Textul care va fi afișat pe rîndul 2
	 *
	 * @throws jpos.JposException ex
	 *
	 * @throws JposException e daca parametrii sunt incorecți. De ex. daca depașesc lungimea maxima de caractere acceptată pe un rînd
	 */
	public void display(String row1, String row2) throws JposException;

	public void display2( String row2) throws JposException;
	/**
	 * Șterge displayul într-un singur pas
	 *
	 * @throws jpos.JposException ex
	 */
	public void clear() throws JposException;

	/**
	 * @return nr max de rînduri
	 */
	public int getRowCount();

	/**
	 * @return nr max de coloane
	 */
	public int getColumnCount();

}
