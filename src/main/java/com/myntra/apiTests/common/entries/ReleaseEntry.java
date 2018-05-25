package com.myntra.apiTests.common.entries;

import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.Constants.ShipmentSource;

import java.util.List;

/**
 * Created by Shubham Gupta on 8/1/17.
 */
public class ReleaseEntry {

	private String releaseId;
	private String packetId;
	private ReleaseStatus toStatus;
	private Boolean force;
	private List<TryNBuyEntry> tryNBuyEntries;
	private ShipmentSource shipmentSource;

	public ReleaseEntry(String releaseOrPacketId, ReleaseStatus toStatus, Boolean force,
			List<TryNBuyEntry> tryNBuyEntries, ShipmentSource shipmentSource) {
		this.releaseId = releaseOrPacketId;
		this.toStatus = toStatus;
		this.force = force;
		this.tryNBuyEntries = tryNBuyEntries;
		this.shipmentSource = shipmentSource;
	}

	public static class Builder {
		protected String _releaseId;
		protected ReleaseStatus _toStatus;
		protected Boolean _force;
		protected List<TryNBuyEntry> _tryNBuyEntries;
		protected ShipmentSource _shipmentSource;

		public Builder(String releaseId, ReleaseStatus toStatus) {
			this._releaseId = releaseId;
			this._toStatus = toStatus;
		}

		public Builder force(Boolean force) {
			this._force = force;
			return this;
		}

		public Builder tryNBuyEntries(List<TryNBuyEntry> tryNBuyEntries) {
			this._tryNBuyEntries = tryNBuyEntries;
			return this;
		}

		public Builder shipmentSource(ShipmentSource shipmentSource) {
			this._shipmentSource = shipmentSource;
			return this;
		}

		public ReleaseEntry build() {
			if (_shipmentSource == null)
				_shipmentSource = ShipmentSource.MYNTRA;
			if (_force == null)
				_force = false;
			return new ReleaseEntry(_releaseId, _toStatus, _force, _tryNBuyEntries, _shipmentSource);
		}

	}

	public String getReleaseId() {
		return releaseId;
	}

	public ReleaseStatus getToStatus() {
		return toStatus;
	}

	public String getPacketId() {
		return packetId;
	}

	public void setPacketId(String packetId) {
		this.packetId = packetId;
	}

	public Boolean getForce() {
		return force;
	}

	public List<TryNBuyEntry> getTryNBuyEntries() {
		return tryNBuyEntries;
	}

	public ShipmentSource getShipmentSource() {
		return shipmentSource;
	}

	@Override
	public String toString() {
		return "ReleaseEntry [releaseId=" + releaseId + ", packetId=" + packetId + ", toStatus=" + toStatus + ", force="
				+ force + ", tryNBuyEntries=" + tryNBuyEntries + ", shipmentSource=" + shipmentSource + "]";
	}

}
