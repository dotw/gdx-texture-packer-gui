package com.crashinvaders.texturepackergui.services.model.filetype;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.*;
import com.crashinvaders.texturepackergui.events.FileTypePropertyChangedEvent;
import com.crashinvaders.texturepackergui.events.FileTypePropertyChangedEvent.Property;
import com.crashinvaders.texturepackergui.services.model.FileTypeType;
import com.crashinvaders.texturepackergui.services.model.PngCompressionType;
import com.crashinvaders.texturepackergui.services.model.compression.PngCompressionModel;
import com.crashinvaders.texturepackergui.services.model.compression.PngtasticCompressionModel;
import com.crashinvaders.texturepackergui.services.model.compression.TinyPngCompressionModel;
import com.crashinvaders.texturepackergui.services.model.compression.ZopfliCompressionModel;
import com.crashinvaders.texturepackergui.utils.CommonUtils;

import java.io.StringWriter;

public class PngFileTypeModel extends FileTypeModel {
    private static final String TAG = PngFileTypeModel.class.getSimpleName();

    private Pixmap.Format encoding = Pixmap.Format.RGBA8888;
    private PngCompressionModel compression = null;

    @Override
    public FileTypeType getType() {
        return FileTypeType.PNG;
    }

    public Pixmap.Format getEncoding() {
        return encoding;
    }

    public void setEncoding(Pixmap.Format encoding) {
        this.encoding = encoding;

        if (eventDispatcher != null) {
            eventDispatcher.postEvent(new FileTypePropertyChangedEvent(this, Property.PNG_ENCODING));
        }
    }

    public <T extends PngCompressionModel> T getCompression() {
        return (T)compression;
    }

    public void setCompression(PngCompressionModel compression) {
        this.compression = compression;

        if (eventDispatcher != null) {
            eventDispatcher.postEvent(new FileTypePropertyChangedEvent(this, Property.PNG_COMPRESSION));
        }
    }

    @Override
    public String serializeState() {
        StringWriter buffer = new StringWriter();
        try {
            Json json = new Json();
            json.setWriter(new JsonWriter(buffer));
            json.writeObjectStart();
            json.writeValue("encoding", encoding.name());
            if (compression != null) {
                json.writeValue("compType", compression.getType());
                json.writeValue("compData", compression.serializeState());
            }
            json.writeObjectEnd();
            return buffer.toString();
        } finally {
            StreamUtils.closeQuietly(buffer);
        }
    }

    @Override
    public void deserializeState(String data) {
        if (data == null) return;

        JsonValue jsonValue = new JsonReader().parse(data);

        this.encoding = CommonUtils.findEnumConstantSafe(Pixmap.Format.class,
                jsonValue.getString("encoding", null), encoding);

        PngCompressionType compType = CommonUtils.findEnumConstantSafe(PngCompressionType.class,
                jsonValue.getString("compType", null), null);
        if (compType != null) {
            PngCompressionModel compModel = null;
            switch (compType) {
                case PNGTASTIC:
                    compModel = new PngtasticCompressionModel();
                    break;
                case ZOPFLI:
                    compModel = new ZopfliCompressionModel();
                    break;
                case TINY_PNG:
                    compModel = new TinyPngCompressionModel();
                    break;
                default:
                    Gdx.app.error(TAG, "Unexpected PngCompressionType: " + compType);
            }
            if (compModel != null) {
                String compData = jsonValue.getString("compData", null);
                compModel.deserializeState(compData);
            }
            this.compression = compModel;
        }
    }
}
