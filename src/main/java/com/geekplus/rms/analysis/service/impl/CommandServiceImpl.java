package com.geekplus.rms.analysis.service.impl;

import org.springframework.stereotype.Service;

import com.geekplus.rms.analysis.service.CommandService;

/**
 * @version : CommandServiceImpl.java
 */
@Service
public class CommandServiceImpl implements CommandService {

    @Override
    public String executeCmd(String cmd) {

        return cmd;
    }
}
