package ir.pint.soltoon.soltoongame.shared.communication;

import ir.pint.soltoon.utils.shared.facades.json.Secure;

import java.io.Serializable;

/**
 * Created by amirkasra on 9/28/2017 AD.
 */

@Secure
public abstract class Message implements Serializable {
    private Long id;
    
    public Message(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
