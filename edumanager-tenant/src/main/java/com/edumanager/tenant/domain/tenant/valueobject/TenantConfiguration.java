package com.edumanager.tenant.domain.tenant.valueobject;

import com.edumanager.tenant.domain.shared.ValueObject;

import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;

/**
 * Value Object: TenantConfiguration
 * Represents configuration settings for a tenant.
 */
public final class TenantConfiguration extends ValueObject {

    private final ZoneId timezone;
    private final Locale locale;
    private final String currency;

    public TenantConfiguration(ZoneId timezone, Locale locale, String currency) {
        this.timezone = timezone != null ? timezone : ZoneId.of("Africa/Ouagadougou");
        this.locale = locale != null ? locale : Locale.FRENCH;
        this.currency = currency != null ? currency : "XOF";
    }

    public ZoneId getTimezone() {
        return timezone;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TenantConfiguration)) return false;
        TenantConfiguration that = (TenantConfiguration) o;
        return Objects.equals(timezone, that.timezone) &&
               Objects.equals(locale, that.locale) &&
               Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timezone, locale, currency);
    }
}
