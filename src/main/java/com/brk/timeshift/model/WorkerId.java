package com.brk.timeshift.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Worker identifier
 */
@Data
@AllArgsConstructor
@Builder
public class WorkerId {

  private final String id;
}
