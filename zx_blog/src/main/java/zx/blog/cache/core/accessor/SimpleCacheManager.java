package zx.blog.cache.core.accessor;

import java.io.Serializable;

import zx.blog.cache.core.CacheTemplate;
import zx.blog.entity.TableRecordVersion;

public abstract class SimpleCacheManager<PK extends Serializable, V extends TableRecordVersion> implements CacheTemplate<V>{

}
