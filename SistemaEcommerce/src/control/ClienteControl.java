/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import model.dao.ClienteDao;
import model.dao.ClienteDaoImpl;
import model.domain.Cliente;
import model.service.ServiceLocator;
import org.jdesktop.observablecollections.ObservableCollections;
import util.ValidacaoException;

/**
 *
 * @author Keilo
 */
public final class ClienteControl {
    private final PropertyChangeSupport propertyChangesSupporte = new PropertyChangeSupport(this);

    private Cliente clienteDigitado;
    private Cliente clienteSelecionado;
    private final List<Cliente> clientesTabela;
    
    private final ClienteDao clienteDao;
    
    public ClienteControl(){
        clienteDao = ServiceLocator.getClienteDao();
        clientesTabela= ObservableCollections.observableList(new ArrayList<Cliente>());
        novo();
        pesquisar();    }    

    public void novo() {
        setClienteDigitado(new Cliente());
    }

    public void pesquisar() {
        clientesTabela.clear();
        clientesTabela.addAll(clienteDao.pesquisar(getClienteDigitado()));
    }

    public void salvar() throws ValidacaoException {
        clienteDigitado.validar();
        clienteDao.salvarAtualizar(clienteDigitado);
        novo();
        pesquisar();
    }
    public void excluir(){
        clienteDao.excluir(getClienteDigitado());
        novo();
        pesquisar();
        
    }
    
    public Cliente getClienteDigitado() {
        return clienteDigitado;
    }

    public void setClienteDigitado(Cliente clienteDigitado) {
        Cliente oldDigitado = this.clienteDigitado;
        this.clienteDigitado = clienteDigitado;
        propertyChangesSupporte.firePropertyChange("clienteDigitado",oldDigitado,clienteDigitado);
    }

    public Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }

    public void setClienteSelecionado(Cliente clienteSelecionado) {
        this.clienteSelecionado = clienteSelecionado;
        if(this.clienteSelecionado!=null){
            setClienteDigitado(clienteSelecionado);
        }
    }

    public ClienteDaoImpl getClienteDao() {
        return (ClienteDaoImpl) clienteDao;
    }

    public void setClienteDao(ClienteDaoImpl clienteDao) {
        //this.clienteDao = clienteDao;
    }
    
    public void addPropertyChangeListener(PropertyChangeListener p ){
        propertyChangesSupporte.addPropertyChangeListener(p);
    }
    public void removePropertyChangeListener(PropertyChangeListener p){
        propertyChangesSupporte.removePropertyChangeListener(p);
    }    
    
}
