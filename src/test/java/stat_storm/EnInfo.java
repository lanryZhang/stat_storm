/*
* EnInfo.java 
* Created on  202016/11/17 15:54 
* Copyright © 2012 Phoenix New Media Limited All Rights Reserved 
*/
package stat_storm;

import com.ifeng.core.data.ILoader;
import com.ifeng.mongo.MongoCodec;

/**
 * Class Description Here
 *
 * @author zhanglr
 * @version 1.0.1
 */
public class EnInfo extends MongoCodec {
    private String guid;
    private String mediaID;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getMediaID() {
        return mediaID;
    }

    public void setMediaID(String mediaID) {
        this.mediaID = mediaID;
    }

    @Override
    public void decode(ILoader loader) {
        this.guid = loader.getString("guid");
        this.mediaID = loader.getString("mediaID");
    }
}
